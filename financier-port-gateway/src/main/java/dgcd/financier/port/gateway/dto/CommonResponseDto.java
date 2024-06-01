package dgcd.financier.port.gateway.dto;

import dgcd.financier.core.api.error.CommonError;
import io.vavr.control.Either;

public record CommonResponseDto(
        Object payload,         // if OK
        String errorMessage     // if error
) {

    public static CommonResponseDto ok(Object payload) {
        return new CommonResponseDto(payload, null);
    }

    public static CommonResponseDto error(String errorMessage) {
        return new CommonResponseDto(null, errorMessage);
    }

    public static CommonResponseDto error(CommonError error) {
        return new CommonResponseDto(null, error.getMessage());
    }

    public static CommonResponseDto fromEither(Either<CommonError, InitDataResponseDto> either) {
        return either.fold(CommonResponseDto::error, CommonResponseDto::ok);
    }

}
