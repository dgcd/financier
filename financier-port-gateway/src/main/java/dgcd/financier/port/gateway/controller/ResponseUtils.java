package dgcd.financier.port.gateway.controller;

import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.port.gateway.dto.CommonResponseDto;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

final class ResponseUtils {

    static ResponseEntity<CommonResponseDto> fromEither(Either<CommonError, ?> result) {
        var dto = CommonResponseDto.fromEither(result);
        var status = result.isRight() ? OK : BAD_REQUEST;
        return new ResponseEntity<>(dto, status);
    }

}
