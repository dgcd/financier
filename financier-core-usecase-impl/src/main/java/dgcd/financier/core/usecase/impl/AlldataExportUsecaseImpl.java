package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.domain.model.Rate;
import dgcd.financier.core.usecase.api.AlldataExportUsecase;
import dgcd.financier.core.usecase.api.dto.AlldataDto;
import dgcd.financier.core.usecase.api.dto.AlldataDto.AccountRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.CategoryRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.OperationRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.RateRow;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.api.port.repository.RatesRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static io.vavr.control.Either.right;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class AlldataExportUsecaseImpl implements AlldataExportUsecase {

    private static final Comparator<Account> ACCOUNTS_COMPARATOR = Comparator
            .comparing(Account::isClosed)
            .thenComparing(Account::getCurrency)
            .thenComparing(Account::getTitle);

    private static final Comparator<Category> CATEGORIES_COMPARATOR = Comparator
            .comparing((Category c) -> c.getParent().getTitle())
            .thenComparing(Category::getTitle);

    private static final Comparator<Operation> OPERATIONS_COMPARATOR = Comparator
            .comparing(Operation::getDate)
            .thenComparing(Operation::getType)
            .thenComparing(Operation::getId);

    private static final Comparator<Rate> RATES_COMPARATOR = Comparator
            .comparing(Rate::getDate);


    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;
    private final RatesRepository ratesRepository;


    @Override
    public Either<CommonError, AlldataDto> execute() {
        var alldataDto = new AlldataDto(
                getAndSortAccounts(),
                getAndSortCategories(),
                getAndSortOperations(),
                getAndSortRates()
        );
        return right(alldataDto);
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


    private List<RateRow> getAndSortRates() {
        return ratesRepository.findAll()
                .stream()
                .sorted(RATES_COMPARATOR)
                .map(RateRow::of)
                .toList();
    }

}
