package dgcd.financier.app.modules.operation;

import dgcd.financier.app.modules.operation.dto.OperationCreateRequestDto;
import dgcd.financier.app.modules.operation.dto.OperationsCreateResponseDto;
import dgcd.financier.app.modules.operation.exceptions.OperationCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dgcd.financier.app.dictionary.OperationType.TRANS;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class OperationsService {

    private final OperationsFacilityService operationsFacilityService;


    public OperationsCreateResponseDto createOperationOrTransfert(OperationCreateRequestDto dto) {
        validateDtoCommon(dto);
        return TRANS.equals(dto.operationType()) ?
                createTransfert(dto) :
                createOperation(dto);
    }

    private void validateDtoCommon(OperationCreateRequestDto dto) {
        if (isNull(dto.amount()) || dto.amount().compareTo(ZERO) == 0) {
            throw new OperationCreateException("Amount can not be " + dto.amount());
        }
    }

    //////////////////////////////////////////////////////////////////


    private OperationsCreateResponseDto createOperation(OperationCreateRequestDto dto) {
        validateDtoForCreateOperation(dto);
        return operationsFacilityService.createOperation(dto);
    }

    private void validateDtoForCreateOperation(OperationCreateRequestDto dto) {
        if (isNull(dto.quantity())) {
            throw new OperationCreateException("Quantity can not be null");
        }
        if (ZERO.compareTo(dto.quantity()) >= 0) {
            throw new OperationCreateException("Quantity must be more then 0");
        }
    }

    //////////////////////////////////////////////////////////////////


    private OperationsCreateResponseDto createTransfert(OperationCreateRequestDto dto) {
        validateDtoForTransfertCreation(dto);
        return operationsFacilityService.createTransfert(dto);
    }

    private void validateDtoForTransfertCreation(OperationCreateRequestDto dto) {
        if (isNull(dto.accountId()) || isNull(dto.accountToId())) {
            throw new OperationCreateException("Account id can not be null");
        }
        if (dto.accountId().equals(dto.accountToId())) {
            throw new OperationCreateException("Account from and account to can not be the same");
        }
    }

}
