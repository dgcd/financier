package dgcd.financier.port.gateway.controller;

import dgcd.financier.core.usecase.api.error.CommonError;
import dgcd.financier.port.gateway.dto.CommonResponseDto;
import io.vavr.control.Either;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

final class ResponseUtils {

    static ResponseEntity<CommonResponseDto> fromEither(Either<CommonError, ?> result) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        var dto = CommonResponseDto.fromEither(result);

        var status = result.isRight() ?
                HttpStatus.OK :
                HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(dto, httpHeaders, status);
    }

}
