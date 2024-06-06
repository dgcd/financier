package dgcd.financier.core.usecase.api;

import dgcd.financier.core.usecase.api.dto.AlldataDto;
import dgcd.financier.core.usecase.api.dto.EmptyDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

@FunctionalInterface
public interface AlldataImportUsecase {

    Either<CommonError, EmptyDto> execute(AlldataDto request);

}
