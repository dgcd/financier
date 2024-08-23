package dgcd.financier.core.usecase.api;

import dgcd.financier.core.usecase.api.dto.common.OperationDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

import java.time.LocalDate;

@FunctionalInterface
public interface OperationEditUsecase {

    Either<CommonError, OperationDto> execute(RequestDto request);


    record RequestDto(
            Long id,
            LocalDate date,
            String comment,
            String counterparty
    ) {}

}
