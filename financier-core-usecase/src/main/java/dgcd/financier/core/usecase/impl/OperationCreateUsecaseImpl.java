package dgcd.financier.core.usecase.impl;

import dgcd.financier.core.domain.Account;
import dgcd.financier.core.domain.Category;
import dgcd.financier.core.domain.Operation;
import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.domain.factory.AccountFactory;
import dgcd.financier.core.domain.factory.OperationFactory;
import dgcd.financier.core.usecase.OperationCreateUsecase;
import dgcd.financier.core.usecase.exception.AccountNotExistsException;
import dgcd.financier.core.usecase.exception.CategoryNotExistsException;
import dgcd.financier.core.usecase.port.repository.AccountsRepository;
import dgcd.financier.core.usecase.port.repository.CategoriesRepository;
import dgcd.financier.core.usecase.port.repository.OperationsRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static dgcd.financier.core.domain.OperationType.BASE;
import static dgcd.financier.core.domain.OperationType.EXCHANGE;
import static dgcd.financier.core.domain.OperationType.TRANS;
import static dgcd.financier.core.usecase.validator.OperationCreateUsecaseRequestValidator.validateCommon;
import static dgcd.financier.core.usecase.validator.OperationCreateUsecaseRequestValidator.validatePair;
import static dgcd.financier.core.usecase.validator.OperationCreateUsecaseRequestValidator.validateSingle;
import static java.math.BigDecimal.ONE;

@RequiredArgsConstructor
public class OperationCreateUsecaseImpl implements OperationCreateUsecase {

    private final AccountsRepository accountsRepository;
    private final CategoriesRepository categoriesRepository;
    private final OperationsRepository operationsRepository;

    @Override
    public Response execute(Request request) {
        validateCommon(request);
        return switch (request.getOperationType()) {
            case INCOME, EXPENSE, BASE -> createSingleOperation(request);
            case TRANS, EXCHANGE -> createPairOperations(request);
        };
    }


    private Response createSingleOperation(Request request) {
        validateSingle(request);

        var account = getAccountFromDbAndCheck(request.getAccountIdentity());

        Category subcategory = null;
        if (request.getOperationType() != BASE) {
            subcategory = getSubcategoryFromDbAndCheck(request.getSubcategoryIdentity());
        }

        var amount = request.getAmount();
        var savedOperation = adjustAccountAndCreateOperation(
                request,
                account,
                subcategory,
                amount,
                false,
                null
        );

        return new Response(
                List.of(savedOperation),
                List.of(savedOperation.getAccount())
        );
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private Response createPairOperations(Request request) {
        validatePair(request);

        var subcategory = getSubcategoryFromDbAndCheck(request.getSubcategoryIdentity());

        var accountFrom = getAccountFromDbAndCheck(request.getAccountIdentity());
        var accountTo = getAccountFromDbAndCheck(request.getAccountToIdentity());
        checkAccountsCurrencies(accountTo, accountFrom, request.getOperationType());

        var amountFrom = request.getAmount().negate();
        var amountTo = TRANS.equals(request.getOperationType()) ?
                request.getAmount() :
                request.getAmountTo();

        // todo: extract prefix generation
        var correlationPrefix = UUID.randomUUID().toString().substring(0, 8);

        var savedOperationFrom = adjustAccountAndCreateOperation(
                request,
                accountFrom,
                subcategory,
                amountFrom,
                true,
                correlationPrefix.concat("_1")
        );

        var savedOperationTo = adjustAccountAndCreateOperation(
                request,
                accountTo,
                subcategory,
                amountTo,
                true,
                correlationPrefix.concat("_2")
        );

        return new Response(
                List.of(savedOperationFrom, savedOperationTo),
                List.of(savedOperationFrom.getAccount(), savedOperationTo.getAccount())
        );
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private Account getAccountFromDbAndCheck(Long identity) {
        var accountOptional = accountsRepository.findByIdentity(identity);
        if (accountOptional.isEmpty()) {
            throw new AccountNotExistsException(identity);
        }
        return accountOptional.get();
    }


    private Category getSubcategoryFromDbAndCheck(Long identity) {
        var subcategoryOptional = categoriesRepository.findById(identity);
        if (subcategoryOptional.isEmpty()) {
            throw new CategoryNotExistsException(identity);
        }
        if (subcategoryOptional.map(Category::getParent).isEmpty()) {
            throw new IllegalStateException("Parent category can not be a subcategory");
        }
        return subcategoryOptional.get();
    }


    private void checkAccountsCurrencies(
            Account accountTo,
            Account accountFrom,
            OperationType operationType
    ) {
        var equalCurrencies = accountFrom.getCurrency().equals(accountTo.getCurrency());
        if (TRANS.equals(operationType) && !equalCurrencies) {
            throw new IllegalArgumentException("Account from and account to must have the same currency");
        }
        if (EXCHANGE.equals(operationType) && equalCurrencies) {
            throw new IllegalArgumentException("Account from and account to must have different currency");
        }
    }


    private Operation adjustAccountAndCreateOperation(
            Request request,
            Account account,
            Category subcategory,
            BigDecimal amount,
            boolean isPaired,
            String correlationId
    ) {
        var adjustedAccount = AccountFactory.makeWithNewBalance(
                account,
                account.getBalance().add(amount)
        );

        var savedAccount = accountsRepository.save(adjustedAccount);

        var operation = OperationFactory.makeNew(
                request.getDate(),
                savedAccount,
                request.getOperationType(),
                amount,
                isPaired ? ONE : request.getQuantity(),
                subcategory,
                request.getComment(),
                request.getCounterparty(),
                correlationId
        );

        return operationsRepository.save(operation);
    }

}
