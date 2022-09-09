package dgcd.financier.app.modules.dataExcel;

import dgcd.financier.app.modules.account.Account;
import dgcd.financier.app.modules.account.AccountsDaoService;
import dgcd.financier.app.modules.category.CategoriesDaoService;
import dgcd.financier.app.modules.category.Category;
import dgcd.financier.app.modules.dataExcel.exceptions.DatabaseIsNotEmptyException;
import dgcd.financier.app.modules.operation.Operation;
import dgcd.financier.app.modules.operation.OperationsDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
class DataExcelDatabaseService {

    private final AccountsDaoService accountsDaoService;
    private final CategoriesDaoService categoriesDaoService;
    private final OperationsDaoService operationsDaoService;


    @Transactional(readOnly = true)
    public AllData readDataForExport() {
        return new AllData(
                getAndSortAccounts(),
                getAndSortCategories(),
                getAndSortOperations()
        );
    }


    private List<Account> getAndSortAccounts() {
        return accountsDaoService.findAll()
                .stream()
                .sorted((a1, a2) -> a1.getIsClosed().equals(a2.getIsClosed()) ?
                        a1.getCurrency().equals(a2.getCurrency()) ?
                                a1.getTitle().compareTo(a2.getTitle()) :
                                a1.getCurrency().compareTo(a2.getCurrency()) :
                        a1.getIsClosed() ? 1 : -1
                )
                .toList();
    }


    private List<Category> getAndSortCategories() {
        return categoriesDaoService.findAll()
                .stream()
                .filter(c -> nonNull(c.getParent()))
                .sorted((c1, c2) ->
                        c1.getParent().getTitle().equals(c2.getParent().getTitle()) ?
                                c1.getTitle().compareTo(c2.getTitle()) :
                                c1.getParent().getTitle().compareTo(c2.getParent().getTitle()))
                .toList();
    }


    private List<Operation> getAndSortOperations() {
        return operationsDaoService.findAllNotCanceled()
                .stream()
                .sorted((o1, o2) -> o1.getDate().equals(o2.getDate()) ?
                        o1.getType().equals(o2.getType()) ?
                                o1.getId().compareTo(o2.getId()) :
                                o1.getType().compareTo(o2.getType()) :
                        o1.getDate().compareTo(o2.getDate())
                )
                .toList();
    }

    /////////////////////////////////////////////////////////////////////////////

    @Transactional
    public void saveParsedData(ParsedData parsedData) {
        checkDatabaseEmptiness();
        var savedAccounts = saveAccounts(parsedData.accounts());
        var savedCategories = saveCategories(parsedData.categories());
        saveOperations(parsedData.operations(), savedAccounts, savedCategories);
    }


    private void checkDatabaseEmptiness() {
        var accounts = accountsDaoService.findAll();
        if (!accounts.isEmpty()) {
            throw new DatabaseIsNotEmptyException();
        }
        var categories = categoriesDaoService.findAll();
        if (!categories.isEmpty()) {
            throw new DatabaseIsNotEmptyException();
        }
        var operations = operationsDaoService.findAll();
        if (!operations.isEmpty()) {
            throw new DatabaseIsNotEmptyException();
        }
    }


    private List<Account> saveAccounts(List<ParsedData.ParsedAccount> parsedAccounts) {
        var accounts = parsedAccounts.stream()
                .map(a -> new Account(
                        null,
                        a.title(),
                        a.currency(),
                        a.balance(),
                        a.isClosed()
                ))
                .toList();
        return accountsDaoService.saveAll(accounts);
    }


    private List<Category> saveCategories(List<ParsedData.ParsedCategory> parsedCategories) {
        var parentCategories = parsedCategories.stream()
                .filter(c -> isNull(c.parentTitle()))
                .map(c -> new Category(
                        null,
                        c.ownTitle(),
                        null
                ))
                .toList();
        var savedParentCategories = categoriesDaoService.saveAll(parentCategories);
        var parentsMap = savedParentCategories.stream()
                .collect(Collectors.toMap(Category::getTitle, c -> c));

        var subcategories = parsedCategories.stream()
                .filter(c -> nonNull(c.parentTitle()))
                .map(c -> new Category(
                        null,
                        c.ownTitle(),
                        parentsMap.get(c.parentTitle())
                ))
                .toList();
        var savedSubcategories = categoriesDaoService.saveAll(subcategories);

        var allCategories = new LinkedList<>(savedParentCategories);
        allCategories.addAll(savedSubcategories);
        return allCategories;
    }


    private void saveOperations(
            List<ParsedData.ParsedOperation> parsedOperations,
            List<Account> savedAccounts,
            List<Category> savedCategories
    ) {
        var accountsMap = savedAccounts.stream()
                .collect(Collectors.toMap(Account::getTitle, account -> account));
        var subcategories = savedCategories.stream()
                .filter(c -> nonNull(c.getParent()))
                .toList();

        var operations = parsedOperations.stream()
                .map(op -> {
                    var subcategory = subcategories.stream()
                            .filter(sc -> Objects.equals(sc.getTitle(), op.subcategoryTitle()) &&
                                    Objects.equals(sc.getParent().getTitle(), op.parentCategoryTitle())
                            )
                            .findFirst()
                            .orElse(null);
                    return new Operation(
                            null,
                            op.date(),
                            accountsMap.get(op.accountTitle()),
                            op.type(),
                            op.amount(),
                            op.quantity(),
                            subcategory,
                            op.comment(),
                            op.counterparty(),
                            false,
                            op.correlationId()
                    );
                })
                .toList();

        operationsDaoService.saveAll(operations);
    }

}
