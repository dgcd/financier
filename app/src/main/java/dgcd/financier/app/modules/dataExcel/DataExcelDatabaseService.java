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

import java.util.ArrayList;
import java.util.List;
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
                .sorted((a1, a2) -> a1.getCurrency().equals(a2.getCurrency()) ?
                        a1.getTitle().compareTo(a2.getTitle()) :
                        a1.getCurrency().compareTo(a2.getCurrency())
                )
                .toList();
    }

    private List<Category> getAndSortCategories() {
        return categoriesDaoService.findAll()
                .stream()
                .sorted((c1, c2) -> {
                    if (isNull(c1.getParent()) && nonNull(c2.getParent())) {
                        return -1;
                    }
                    if (nonNull(c1.getParent()) && isNull(c2.getParent())) {
                        return 1;
                    }
                    return 0;
                })
                .sorted((c1, c2) -> {
                    if (isNull(c1.getParent()) && isNull(c2.getParent())) {
                        return c1.getTitle().compareTo(c2.getTitle());
                    }
                    if (isNull(c1.getParent()) || isNull(c2.getParent())) {
                        return 0;
                    }
                    return c1.getParent().getTitle().compareTo(c2.getParent().getTitle());
                })
                .toList();
    }

    private List<Operation> getAndSortOperations() {
        return operationsDaoService.findAll()
                .stream()
                .sorted((o1, o2) -> o1.getDate().equals(o2.getDate()) ?
                        o1.getId().compareTo(o2.getId()) :
                        o1.getDate().compareTo(o2.getDate())
                )
                .toList();
    }

    /////////////////////////////////////////////////////////////////////////////

    @Transactional
    public AllData saveParsedData(ParsedData parsedData) {
        checkDatabaseEmptiness();

        var savedAccounts = saveAccounts(parsedData.accounts());
        var savedCategories = saveCategories(parsedData.categories());

        // todo: delete
        savedAccounts.forEach(System.out::println);
        savedCategories.forEach(System.out::println);

        return new AllData(
                savedAccounts,
                savedCategories,
                List.of()
        );
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
        var parentsMap = savedParentCategories.stream().collect(Collectors.toMap(Category::getTitle, c -> c));

        var subcategories = parsedCategories.stream()
                .filter(c -> nonNull(c.parentTitle()))
                .map(c -> new Category(
                        null,
                        c.ownTitle(),
                        parentsMap.get(c.parentTitle())
                ))
                .toList();
        var savedSubcategories = categoriesDaoService.saveAll(subcategories);

        var allCategories = new ArrayList<>(savedParentCategories);
        allCategories.addAll(savedSubcategories);
        return allCategories;
    }

}
