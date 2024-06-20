package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.domain.model.Rate;
import dgcd.financier.core.usecase.api.AlldataImportUsecase;
import dgcd.financier.core.usecase.api.dto.AlldataDto;
import dgcd.financier.core.usecase.api.dto.AlldataDto.AccountRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.CategoryRow;
import dgcd.financier.core.usecase.api.dto.AlldataDto.OperationRow;
import dgcd.financier.core.usecase.api.dto.EmptyDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.api.port.repository.MiscRepository;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.api.port.repository.RatesRepository;
import dgcd.financier.core.usecase.api.utils.EitherUtils;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.usecase.api.dto.EmptyDto.EMPTY;
import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.DATABASE_NOT_EMPTY;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class AlldataImportUsecaseImpl implements AlldataImportUsecase {

    private final MiscRepository miscRepository;
    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;
    private final RatesRepository ratesRepository;


    @Override
    public Either<CommonError, EmptyDto> execute(AlldataDto request) {
        return toRight(new Context(request))
                .flatMap(this::checkDatabaseEmptiness)
                .flatMap(this::saveAccounts)
                .flatMap(this::saveCategories)
                .flatMap(this::saveOperations)
                .flatMap(this::saveRates)
                .map(_ -> EMPTY);
    }


    private Either<CommonError, Context> checkDatabaseEmptiness(Context context) {
        return miscRepository.databaseIsEmpty() ?
                right(context) :
                left(DATABASE_NOT_EMPTY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    private Either<CommonError, Context> saveAccounts(Context context) {
        return toRight(context.getRequest().accounts())
                .flatMap(this::createAndValidateAccounts)
                .map(accountsRepository::createAll)
                .map(context::setAccounts);
    }

    private Either<CommonError, List<Account>> createAndValidateAccounts(List<AccountRow> accountRows) {
        return Try.of(() -> createAndValidateAccountsInner(accountRows))
                .fold(EitherUtils::toLeft, EitherUtils::toRight);
    }

    private List<Account> createAndValidateAccountsInner(List<AccountRow> accountRows) {
        return accountRows.stream()
                .map(this::createAndValidate)
                .toList();
    }

    private Account createAndValidate(AccountRow a) {
        return new Account()
                .setTitle(a.title())
                .setCurrency(a.currency())
                .setBalance(a.balance())
                .setClosed(a.isClosed())
                .validate();
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    private Either<CommonError, Context> saveCategories(Context context) {
        return toRight(context.getRequest().categories())
                .flatMap(this::createAndValidateParents)
                .map(categoriesRepository::createAll)
                .map(this::parentsToMap)
                .map(context::setParentsMap)
                .flatMap(this::createAndValidateSubcategories)
                .map(categoriesRepository::createAll)
                .map(subcategories -> {
                    context.getCategories().addAll(subcategories);
                    context.getCategories().addAll(context.getParentsMap().values());
                    return context;
                });
    }


    private Either<CommonError, List<Category>> createAndValidateParents(List<CategoryRow> categoryRows) {
        return Try.of(() -> createAndValidateParentsInner(categoryRows))
                .fold(EitherUtils::toLeft, EitherUtils::toRight);
    }

    private List<Category> createAndValidateParentsInner(List<CategoryRow> categoryRows) {
        return categoryRows.stream()
                .map(CategoryRow::parentTitle)
                .distinct()
                .map(this::createAndValidateParent)
                .toList();
    }

    private Category createAndValidateParent(String title) {
        return new Category()
                .setTitle(title)
                .validate();
    }


    private Map<String, Category> parentsToMap(List<Category> parents) {
        return parents.stream().collect(Collectors.toMap(Category::getTitle, c -> c));
    }


    private Either<CommonError, List<Category>> createAndValidateSubcategories(Context context) {
        return Try.of(() -> createAndValidateSubcategoriesInner(context))
                .fold(EitherUtils::toLeft, EitherUtils::toRight);
    }

    private List<Category> createAndValidateSubcategoriesInner(Context context) {
        return context.getRequest().categories().stream()
                .map(cr -> createAndValidateSubcategory(cr, context.getParentsMap()))
                .toList();
    }

    private Category createAndValidateSubcategory(CategoryRow categoryRow, Map<String, Category> parentsMap) {
        return new Category()
                .setTitle(categoryRow.ownTitle())
                .setParentId(parentsMap.get(categoryRow.parentTitle()).getId())
                .validate();
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    private Either<CommonError, Context> saveOperations(Context context) {
        var accountsMap = context.getAccounts().stream()
                .collect(Collectors.toMap(Account::getTitle, account -> account));
        var subcategories = context.getCategories().stream()
                .filter(c -> nonNull(c.getParent()))
                .toList();

        var operations = context.getRequest().operations().stream()
                .map(op -> {
                    // todo: optimize
                    var subcategory = BASE.equals(op.type()) ?
                            null :
                            subcategories.stream()
                                    .filter(sc -> isSubcategoryForOperation(op, sc))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalStateException("Can not find subcategory for operation"));
                    return new Operation()
                            .setDate(op.date())
                            .setAccountId(accountsMap.get(op.accountTitle()).getId())
                            .setType(op.type())
                            .setQuantity(op.quantity())
                            .setAmount(op.amount())
                            .setSubcategoryId(subcategory == null ? null : subcategory.getId())
                            .setComment(op.comment())
                            .setCounterparty(op.counterparty())
                            .setCorrelationId(op.correlationId())
                            .validate();
                })
                .toList();

        operationsRepository.createAll(operations);
        return toRight(context);
    }


    private static boolean isSubcategoryForOperation(OperationRow op, Category sc) {
        return Objects.equals(sc.getTitle(), op.subcategoryTitle()) &&
                Objects.equals(sc.getParent().getTitle(), op.parentCategoryTitle());
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    private Either<CommonError, Context> saveRates(Context context) {
        return toRight(context.getRequest().rates())
                .flatMap(this::createAndValidateRates)
                .peek(ratesRepository::createAll)
                .map(_ -> context);
    }

    private Either<CommonError, List<Rate>> createAndValidateRates(List<AlldataDto.RateRow> rateRows) {
        return Try.of(() -> rateRows.stream()
                        .map(this::createAndValidate)
                        .toList())
                .fold(EitherUtils::toLeft, EitherUtils::toRight);
    }

    private Rate createAndValidate(AlldataDto.RateRow r) {
        return new Rate()
                .setDate(r.date())
                .setUsd(r.usdRate())
                .setEur(r.eurRate())
                .validate();
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    @Getter
    @Setter
    @Accessors(chain = true)
    @RequiredArgsConstructor
    private static class Context {

        // request
        private final AlldataDto request;

        // intermediate
        private List<Account> accounts;
        private Map<String, Category> parentsMap;
        private List<Category> categories = new ArrayList<>();

    }

}
