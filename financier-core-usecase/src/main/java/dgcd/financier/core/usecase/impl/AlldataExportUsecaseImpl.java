package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.Rates;
import dgcd.financier.core.usecase.AlldataExportUsecase;
import dgcd.financier.core.usecase.AlldataUsecase;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.port.repository.RatesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class AlldataExportUsecaseImpl implements AlldataExportUsecase {

    private static final Comparator<Account> ACCOUNTS_COMPARATOR = Comparator
            .comparing(Account::getIsClosed)
            .thenComparing(Account::getCurrency)
            .thenComparing(Account::getTitle);

    private static final Comparator<Category> CATEGORIES_COMPARATOR = Comparator
            .comparing((Category c) -> c.getParent().getTitle())
            .thenComparing(Category::getTitle);

    private static final Comparator<Operation> OPERATIONS_COMPARATOR = Comparator
            .comparing(Operation::getDate)
            .thenComparing(Operation::getType)
            .thenComparing(Operation::getIdentity);

    private static final Comparator<Rates> RATES_COMPARATOR = Comparator
            .comparing(Rates::getDate);


    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;
    private final RatesRepository ratesRepository;


    @Override
    public Response execute(Request request) {
        return new Response(new AlldataUsecase.AlldataRows(
                getAndSortAccounts(),
                getAndSortCategories(),
                getAndSortOperations(),
                getAndSortRates()
        ));
    }


    private List<AccountRow> getAndSortAccounts() {
        return accountsRepository.findAll()
                .stream()
                .sorted(ACCOUNTS_COMPARATOR)
                .map(AccountRow::of)
                .toList();
    }


    private List<CategoryRow> getAndSortCategories() {
        return categoriesRepository.findAll()
                .stream()
                .filter(c -> nonNull(c.getParent()))
                .sorted(CATEGORIES_COMPARATOR)
                .map(CategoryRow::of)
                .toList();
    }


    private List<OperationRow> getAndSortOperations() {
        return operationsRepository.findAllNotCanceled()
                .stream()
                .sorted(OPERATIONS_COMPARATOR)
                .map(OperationRow::of)
                .toList();
    }


    private List<RatesRow> getAndSortRates() {
        return ratesRepository.findAll()
                .stream()
                .sorted(RATES_COMPARATOR)
                .map(RatesRow::of)
                .toList();
    }

}
