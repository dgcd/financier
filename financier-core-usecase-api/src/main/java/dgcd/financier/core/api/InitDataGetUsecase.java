package dgcd.financier.core.api;

import dgcd.financier.core.api.dto.InitDataGetResponseDto;
import dgcd.financier.core.api.error.CommonError;
import io.vavr.control.Either;

@FunctionalInterface
public interface InitDataGetUsecase {

    Either<CommonError, InitDataGetResponseDto> execute();

}
