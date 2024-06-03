package dgcd.financier.core.usecase.api;

import dgcd.financier.core.domain.OperationType;
import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.core.usecase.api.dto.common.OperationDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@FunctionalInterface
public interface OperationCreateUsecase {

    Either<CommonError, ResponseDto> execute(RequestDto request);


    record RequestDto(
            LocalDate date,
            Long accountId,
            // for pair operations only
            Long accountToId,

            OperationType operationType,

            BigDecimal quantity,
            BigDecimal amount,
            // for EXCHANGE operations only
            BigDecimal amountTo,

            Long subcategoryId,

            String comment,
            String counterparty
    ) {}


    record ResponseDto(
            List<OperationDto> newOperations,
            List<AccountDto> updatedAccounts
    ) {}

}
