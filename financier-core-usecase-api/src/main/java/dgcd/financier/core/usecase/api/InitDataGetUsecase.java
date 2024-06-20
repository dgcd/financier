package dgcd.financier.core.usecase.api;

import dgcd.financier.core.usecase.api.dto.InitDataGetResponseDto;
import dgcd.financier.core.usecase.api.error.CommonError;
import io.vavr.control.Either;

@FunctionalInterface
public interface InitDataGetUsecase {

    Either<CommonError, InitDataGetResponseDto> execute();

}
