package dgcd.financier.port.gateway.dto;

import dgcd.financier.core.usecase.api.error.CommonError;
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
        return new CommonResponseDto(null, error.message());
    }

    public static CommonResponseDto fromEither(Either<CommonError, ?> either) {
        return either.fold(CommonResponseDto::error, CommonResponseDto::ok);
    }

}
