package dgcd.financier.app.modules.operation;

import dgcd.financier.app.infrastructure.dto.CommonIdDto;
import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationsCancelResponseDto;
import dgcd.financier.app.modules.operation.dto.OperationsCreateResponseDto;
import dgcd.financier.app.modules.operation.exceptions.OperationCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dgcd.financier.app.dictionary.OperationType.BASE;
import static dgcd.financier.app.dictionary.OperationType.EXCHANGE;
import static dgcd.financier.app.dictionary.OperationType.TRANS;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
class OperationsService {

    private final OperationsFacilityService operationsFacilityService;


    public OperationsCreateResponseDto createOperation(OperationCreateRequestDto dto) {
        validateDtoCommon(dto);
        return switch (dto.operationType()) {
            case INCOME, EXPENSE, BASE -> createSingleOperation(dto);
            case TRANS, EXCHANGE -> createPairOperations(dto);
        };
    }

    private void validateDtoCommon(OperationCreateRequestDto dto) {
        if (isNull(dto.amount()) || dto.amount().compareTo(ZERO) == 0) {
            throw new OperationCreateException("Amount can not be " + dto.amount());
        }
        if (dto.operationType() == BASE && nonNull(dto.subcategoryId())) {
            throw new OperationCreateException("Base operation can not have category");
        }
        if (dto.operationType() != BASE && isNull(dto.subcategoryId())) {
            throw new OperationCreateException("Operation must have category");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private OperationsCreateResponseDto createSingleOperation(OperationCreateRequestDto dto) {
        validateDtoForSingleOperation(dto);
        return operationsFacilityService.createSingleOperation(dto);
    }

    private void validateDtoForSingleOperation(OperationCreateRequestDto dto) {
        if (nonNull(dto.accountToId())) {
            throw new OperationCreateException("Second account id must be be null");
        }
        if (isNull(dto.quantity())) {
            throw new OperationCreateException("Quantity can not be null");
        }
        if (ZERO.compareTo(dto.quantity()) >= 0) {
            throw new OperationCreateException("Quantity must be more then 0");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private OperationsCreateResponseDto createPairOperations(OperationCreateRequestDto dto) {
        validateDtoForPairOperations(dto);
        return operationsFacilityService.createPairOperations(dto);
    }

    private void validateDtoForPairOperations(OperationCreateRequestDto dto) {
        if (isNull(dto.accountToId())) {
            throw new OperationCreateException("Second account id can not be null");
        }
        if (dto.accountId().equals(dto.accountToId())) {
            throw new OperationCreateException("Account from and account to can not be the same");
        }
        if (nonNull(dto.quantity())) {
            throw new OperationCreateException("Quantity must be null");
        }
        if (isNull(dto.subcategoryId())) {
            throw new OperationCreateException("Operation must have category");
        }
        if (TRANS.equals(dto.operationType()) && nonNull(dto.amountTo())) {
            throw new OperationCreateException("Trans operation can not have second amount");
        }
        if (EXCHANGE.equals(dto.operationType()) && (isNull(dto.amountTo()) || dto.amountTo().compareTo(ZERO) == 0)) {
            throw new OperationCreateException("Exchange operation must have second amount");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////


    public OperationsCancelResponseDto cancelOperation(CommonIdDto dto) {
        return operationsFacilityService.cancelOperation(dto);
    }

}
