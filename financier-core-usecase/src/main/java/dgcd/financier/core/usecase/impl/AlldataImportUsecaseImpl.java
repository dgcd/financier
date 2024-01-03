package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.domain.factory.CategoryFactory;
import dgcd.financier.core.domain.factory.OperationFactory;
import dgcd.financier.core.usecase.AlldataImportUsecase;
import dgcd.financier.core.usecase.exception.DatabaseIsNotEmptyException;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static dgcd.financier.core.domain.OperationType.BASE;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class AlldataImportUsecaseImpl implements AlldataImportUsecase {

    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;

    @Override
    public Response execute(Request request) {
        checkDatabaseEmptiness();
        var alldataRows = request.getAlldataRows();
        var savedAccounts = saveAccounts(alldataRows.accounts());
        var savedCategories = saveCategories(alldataRows.categories());
        saveOperations(alldataRows.operations(), savedAccounts, savedCategories);
        return new Response();
    }


    private void checkDatabaseEmptiness() {
        // todo: change for count
        var accounts = accountsRepository.findAll();
        if (!accounts.isEmpty()) {
            throw new DatabaseIsNotEmptyException();
        }
        var categories = categoriesRepository.findAll();
        if (!categories.isEmpty()) {
            throw new DatabaseIsNotEmptyException();
        }
        var operations = operationsRepository.findAll();
        if (!operations.isEmpty()) {
            throw new DatabaseIsNotEmptyException();
        }
    }


    private List<Account> saveAccounts(@NonNull List<AccountRow> accountRows) {
        var accounts = accountRows.stream()
                .map(a -> AccountFactory.makeExisting(
                        null,
                        a.title(),
                        a.currency(),
                        a.balance(),
                        a.isClosed()
                ))
                .toList();
        return accountsRepository.saveAll(accounts);
    }


    private List<Category> saveCategories(List<CategoryRow> categoryRows) {
        var parentCategories = categoryRows.stream()
                .map(CategoryRow::parentTitle)
                .distinct()
                .map(CategoryFactory::makeNewParent)
                .toList();
        var savedParentCategories = categoriesRepository.saveAll(parentCategories);
        var parentsMap = savedParentCategories.stream()
                .collect(Collectors.toMap(Category::getTitle, c -> c));

        var subcategories = categoryRows.stream()
                .map(c -> CategoryFactory.makeNewSubcategory(
                        c.ownTitle(),
                        parentsMap.get(c.parentTitle())
                ))
                .toList();
        var savedSubcategories = categoriesRepository.saveAll(subcategories);

        var allCategories = new ArrayList<Category>(savedParentCategories.size() + savedSubcategories.size());
        allCategories.addAll(savedParentCategories);
        allCategories.addAll(savedSubcategories);
        return allCategories;
    }


    private void saveOperations(
            List<OperationRow> parsedOperations,
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
                    // todo: optimize
                    var subcategory = BASE.equals(op.type()) ?
                            null :
                            subcategories.stream()
                                    .filter(sc -> isSubcategoryForOperation(op, sc))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalStateException("Can not find subcategory for operation"));
                    return OperationFactory.makeNew(
                            op.date(),
                            accountsMap.get(op.accountTitle()),
                            op.type(),
                            op.amount(),
                            op.quantity(),
                            subcategory,
                            op.comment(),
                            op.counterparty(),
                            op.correlationId()
                    );
                })
                .toList();

        operationsRepository.saveAll(operations);
    }


    private static boolean isSubcategoryForOperation(OperationRow op, Category sc) {
        return Objects.equals(sc.getTitle(), op.subcategoryTitle()) &&
                Objects.equals(sc.getParent().getTitle(), op.parentCategoryTitle());
    }

}
