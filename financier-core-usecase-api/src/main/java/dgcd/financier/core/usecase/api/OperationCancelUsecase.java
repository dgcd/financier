package dgcd.financier.core.usecase.api;

import dgcd.financier.core.usecase.api.dto.common.AccountDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

import java.util.List;

@FunctionalInterface
public interface OperationCancelUsecase {

    Either<CommonError, ResponseDto> execute(Long operationId);


    record ResponseDto(
            List<Long> canceledOperationsIds,
            List<AccountDto> updatedAccounts
    ) {}

}
