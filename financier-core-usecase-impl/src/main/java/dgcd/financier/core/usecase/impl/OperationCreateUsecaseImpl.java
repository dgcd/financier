package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.model.Account;
import dgcd.financier.core.domain.model.Category;
import dgcd.financier.core.domain.model.Operation;
import dgcd.financier.core.usecase.api.OperationCreateUsecase;
import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.core.usecase.api.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.api.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.api.port.repository.OperationsRepository;
import dgcd.financier.core.usecase.api.utils.EitherUtils;
import dgcd.financier.core.usecase.impl.error.UsecaseError;
import dgcd.financier.core.usecase.impl.mapper.AccountMapper;
import dgcd.financier.core.usecase.impl.mapper.OperationMapper;
import dgcd.financier.core.usecase.impl.validator.OperationCreateUsecaseValidator;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.OperationType.EXCHANGE;
import static dgcd.financier.core.domain.OperationType.TRANS;
import static dgcd.financier.core.usecase.api.utils.EitherUtils.toRight;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_CLOSED;
import static dgcd.financier.core.usecase.impl.error.Errors.ACCOUNT_NOT_FOUND;
import static dgcd.financier.core.usecase.impl.error.Errors.CATEGORY_CAN_NOT_BE_PARENT;
import static dgcd.financier.core.usecase.impl.error.Errors.CATEGORY_NOT_FOUND;
import static dgcd.financier.core.usecase.impl.error.Errors.CURRENCIES_DIFFER;
import static dgcd.financier.core.usecase.impl.error.Errors.CURRENCIES_MATCH;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.math.BigDecimal.ONE;
import static java.util.function.Predicate.not;

@RequiredArgsConstructor
public class OperationCreateUsecaseImpl implements OperationCreateUsecase {

    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;


    @Override
    public Either<CommonError, ResponseDto> execute(RequestDto request) {
        return toRight(request)
                .flatMap(OperationCreateUsecaseValidator::validateRequest)
                .map(Context::new)
                .flatMap(ctx -> ctx.isPaired() ?
                        createPairOperations(ctx) :
                        createSingleOperation(ctx)
                )
                .map(this::mapResponse);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private Either<CommonError, Context> createSingleOperation(Context context) {
        return toRight(context)
                .flatMap(this::retrieveAccount)
                .flatMap(this::retrieveSubcategory)
                .flatMap(this::createSingleOperationUpdateAccount);
    }


    private Either<CommonError, Context> createSingleOperationUpdateAccount(Context context) {
        return createOperationUpdateAccount(
                context.getRequest(),
                context.getAccount(),
                context.getSubcategory(),
                context.getRequest().amount(),
                false,
                null
        ).map(context::toResult);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private Either<CommonError, Context> createPairOperations(Context context) {
        return toRight(context)
                .flatMap(this::retrieveAccount)
                .flatMap(this::retrieveAccountTo)
                .flatMap(this::checkAccountsCurrencies)
                .flatMap(this::retrieveSubcategory)
                .flatMap(this::generateCorrelationPrefix)
                .flatMap(this::createOperationUpdateAccountFrom)
                .flatMap(this::createOperationUpdateAccountTo);
    }


    private Either<CommonError, Context> checkAccountsCurrencies(Context context) {
        var accountFrom = context.getAccount();
        var accountTo = context.getAccountTo();
        var type = context.getRequest().operationType();

        var equalCurrencies = accountFrom.getCurrency().equals(accountTo.getCurrency());
        if (TRANS.equals(type) && !equalCurrencies) {
            return left(CURRENCIES_DIFFER);
        }
        if (EXCHANGE.equals(type) && equalCurrencies) {
            return left(CURRENCIES_MATCH);
        }

        return right(context);
    }


    private Either<CommonError, Context> generateCorrelationPrefix(Context context) {
        return toRight(UUID.randomUUID().toString().substring(0, 8))
                .map(context::setCorrelationPrefix);
    }


    private Either<CommonError, Context> createOperationUpdateAccountFrom(Context context) {
        return createOperationUpdateAccount(
                context.getRequest(),
                context.getAccount(),
                context.getSubcategory(),
                context.getRequest().amount().negate(),
                true,
                context.getCorrelationPrefix().concat("_1")
        ).map(context::toResult);
    }


    private Either<CommonError, Context> createOperationUpdateAccountTo(Context context) {
        var request = context.getRequest();
        var amount = TRANS.equals(request.operationType()) ?
                request.amount() :
                request.amountTo();
        return createOperationUpdateAccount(
                request,
                context.getAccountTo(),
                context.getSubcategory(),
                amount,
                true,
                context.getCorrelationPrefix().concat("_2")
        ).map(context::toResult);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private Either<CommonError, Context> retrieveAccount(Context context) {
        return toRight(context.getRequest().accountId())
                .flatMap(this::retrieveAccountInner)
                .map(context::setAccount);
    }

    private Either<CommonError, Context> retrieveAccountTo(Context context) {
        return toRight(context.getRequest().accountToId())
                .flatMap(this::retrieveAccountInner)
                .map(context::setAccountTo);
    }

    private Either<CommonError, Account> retrieveAccountInner(Long accountId) {
        return accountsRepository.findById(accountId)
                .map(EitherUtils::toRight)
                .orElseGet(() -> left(ACCOUNT_NOT_FOUND))
                .flatMap(this::checkIfClosed);
    }

    private Either<CommonError, Account> checkIfClosed(Account account) {
        return Option.of(account)
                .filter(not(Account::isClosed))
                .map(EitherUtils::toRight)
                .getOrElse(() -> left(ACCOUNT_CLOSED));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private Either<CommonError, Context> retrieveSubcategory(Context context) {
        var operationType = context.getRequest().operationType();
        if (BASE.equals(operationType)) {
            return right(context);
        }

        var subcategoryId = context.getRequest().subcategoryId();
        return categoriesRepository.findById(subcategoryId)
                .map(EitherUtils::toRight)
                .orElseGet(() -> left(CATEGORY_NOT_FOUND))
                .flatMap(this::checkIfParent)
                .map(context::setSubcategory);
    }


    private Either<CommonError, Category> checkIfParent(Category category) {
        return Option.of(category)
                .filter(not(Category::isParent))
                .map(EitherUtils::toRight)
                .getOrElse(() -> left(CATEGORY_CAN_NOT_BE_PARENT));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private Either<CommonError, Operation> createOperationUpdateAccount(
            RequestDto request,
            Account account,
            Category subcategory,
            BigDecimal amount,
            boolean isPaired,
            String correlationId
    ) {
        try {
            var balance = account.getBalance().add(amount);
            account.setBalance(balance).validate();

            var savedAccount = accountsRepository.update(account);

            var type = request.operationType();
            var quantity = isPaired ? ONE : request.quantity();
            var subcategoryId = BASE.equals(type) ? null : subcategory.getId();
            var operation = new Operation()
                    .setDate(request.date())
                    .setAccountId(savedAccount.getId())
                    .setType(type)
                    .setQuantity(quantity)
                    .setAmount(amount)
                    .setSubcategoryId(subcategoryId)
                    .setComment(request.comment())
                    .setCounterparty(request.counterparty())
                    .setCorrelationId(correlationId)
                    .setSubcategory(subcategory).setAccount(account)   // todo: fix findById problem and delete
                    .validate();

            var savedOperation = operationsRepository.save(operation);
            return right(savedOperation);
        } catch (IllegalArgumentException ex) {
            return left(new UsecaseError(ex.getMessage()));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private ResponseDto mapResponse(Context context) {
        var operations = context.getOperations().stream()
                .map(OperationMapper.INSTANCE::fromDomain)
                .toList();
        var accounts = context.getAccounts().stream()
                .map(AccountMapper.INSTANCE::fromDomain)
                .toList();
        return new ResponseDto(operations, accounts);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    @Getter
    @Setter
    @Accessors(chain = true)
    @RequiredArgsConstructor
    private static class Context {

        // request
        private final RequestDto request;

        // intermediate
        private Account account;
        private Account accountTo;
        private Category subcategory;
        private String correlationPrefix;

        // result
        private final List<Account> accounts = new ArrayList<>();
        private final List<Operation> operations = new ArrayList<>();


        boolean isPaired() {
            return request.operationType().isPaired();
        }

        Context toResult(Operation operation) {
            this.operations.add(operation);
            this.accounts.add(operation.getAccount());
            return this;
        }

    }

}
