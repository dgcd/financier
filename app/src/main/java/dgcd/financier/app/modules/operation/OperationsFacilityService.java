package dgcd.financier.app.modules.operation;

import dgcd.financier.app.modules.account.AccountsDaoService;
import dgcd.financier.app.modules.account.dto.AccountResponseDto;
import dgcd.financier.app.modules.category.CategoriesDaoService;
import dgcd.financier.app.modules.category.Category;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationsCreateResponseDto;
import dgcd.financier.app.modules.operation.exceptions.OperationCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
                dto.counterparty()
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

        var operationFrom = new Operation(
                null,
                dto.date(),
                accountFrom,
                dto.operationType(),
                dto.amount().negate(),
                ONE,
                subcategory,
                dto.comment(),
                dto.counterparty()
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
                dto.counterparty()
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

}
