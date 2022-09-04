package dgcd.financier.app.modules.operation;

import dgcd.financier.app.infrastructure.dto.CommonIdDto;
import dgcd.financier.app.modules.account.AccountsDaoService;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.CategoriesDaoService;
import dgcd.financier.app.modules.category.Category;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationsCancelResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationsCreateResponseDto;
import dgcd.financier.app.modules.operation.exceptions.OperationCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dgcd.financier.app.dictionary.OperationType.BASE;
import static dgcd.financier.app.dictionary.OperationType.TRANS;
import static java.math.BigDecimal.ONE;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
class OperationsFacilityService {

    private final OperationsDaoService operationsDaoService;
    private final AccountsDaoService accountsDaoService;
    private final CategoriesDaoService categoriesDaoService;


    @Transactional
    public OperationsCreateResponseDto createSingleOperation(OperationCreateRequestDto dto) {
        var account = accountsDaoService.findByIdOrElseThrow(dto.accountId());

        Category subcategory = null;
        if (dto.operationType() != BASE) {
            subcategory = getSubcategoryAndCheckParent(dto);
        }

        var operation = new Operation(
                null,
                dto.date(),
                account,
                dto.operationType(),
                dto.amount(),
                dto.quantity(),
                subcategory,
                dto.comment(),
                dto.counterparty(),
                false,
                null
        );

        account.setBalance(account.getBalance().add(operation.getAmount()));

        operation = operationsDaoService.save(operation);

        return new OperationsCreateResponseDto(
                List.of(OperationResponseDto.of(operation)),
                List.of(AccountResponseDto.of(account))
        );
    }


    @Transactional
    public OperationsCreateResponseDto createPairOperations(OperationCreateRequestDto dto) {
        var accountFrom = accountsDaoService.findByIdOrElseThrow(dto.accountId());
        var accountTo = accountsDaoService.findByIdOrElseThrow(dto.accountToId());

        var subcategory = getSubcategoryAndCheckParent(dto);

        BigDecimal amountTo;
        var equalCurrencies = accountFrom.getCurrency().equals(accountTo.getCurrency());
        if (TRANS.equals(dto.operationType())) {
            if (!equalCurrencies) {
                throw new OperationCreateException("Account from and account to must have the same currency");
            }
            amountTo = dto.amount();
        } else {
            if (equalCurrencies) {
                throw new OperationCreateException("Account from and account to must have different currency");
            }
            amountTo = dto.amountTo();
        }

        var correlationId = UUID.randomUUID().toString();

        var operationFrom = new Operation(
                null,
                dto.date(),
                accountFrom,
                dto.operationType(),
                dto.amount().negate(),
                ONE,
                subcategory,
                dto.comment(),
                dto.counterparty(),
                false,
                correlationId.concat("_1")
        );

        var operationTo = new Operation(
                null,
                dto.date(),
                accountTo,
                dto.operationType(),
                amountTo,
                ONE,
                subcategory,
                dto.comment(),
                dto.counterparty(),
                false,
                correlationId.concat("_2")
        );

        accountTo.setBalance(accountTo.getBalance().add(operationTo.getAmount()));
        accountFrom.setBalance(accountFrom.getBalance().add(operationFrom.getAmount()));

        operationFrom = operationsDaoService.save(operationFrom);
        operationTo = operationsDaoService.save(operationTo);

        return new OperationsCreateResponseDto(
                List.of(
                        OperationResponseDto.of(operationFrom),
                        OperationResponseDto.of(operationTo)
                ),
                List.of(
                        AccountResponseDto.of(accountFrom),
                        AccountResponseDto.of(accountTo)
                )
        );
    }


    private Category getSubcategoryAndCheckParent(OperationCreateRequestDto dto) {
        var subcategory = categoriesDaoService.findByIdOrElseThrow(dto.subcategoryId());
        if (isNull(subcategory.getParent())) {
            throw new OperationCreateException("Subcategory must have parent category");
        }
        return subcategory;
    }


    @Transactional
    public OperationsCancelResponseDto cancelOperation(CommonIdDto dto) {
        var operation = operationsDaoService.findByIdOrElseThrow(dto.id());
        var corrId = operation.getCorrelationId();

        List<Operation> operations;
        if (isNull(corrId)) {
            operations = List.of(operation);
        } else {
            var length = corrId.length() - 2;
            Assert.isTrue(length >= 2 && length <= 36, "Wrong correlation id length");
            var suffix = corrId.substring(0, length - 1);
            System.out.println(suffix);
            operations = operationsDaoService.findByCorrelationIdStartingWith(suffix);
            Assert.isTrue(operations.size() == 2, "Wrong number of correlated operations: " + operations.size());
        }

        var operationsIds = new ArrayList<Long>(2);
        var updatedAccounts = new ArrayList<AccountResponseDto>(2);
        for (var op : operations) {
            System.out.println(op);
            if (op.getIsCanceled()) {
                throw new IllegalStateException(String.format("Operation with id '%d' is already canceled", op.getId()));
            }
            var acc = op.getAccount();
            acc.setBalance(acc.getBalance().subtract(op.getAmount()));
            op.setIsCanceled(true);
            operationsDaoService.save(op);

            operationsIds.add(op.getId());
            updatedAccounts.add(AccountResponseDto.of(acc));
        }

        return new OperationsCancelResponseDto(
                operationsIds,
                updatedAccounts
        );
    }

}
