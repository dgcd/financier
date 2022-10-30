package dgcd.financier.app.infrastructure.exception;

import dgcd.financier.app.infrastructure.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResponseDto> validationException(MethodArgumentNotValidException exception) {
        var firstError = exception.getBindingResult().getAllErrors().iterator().next();
        return makeResponse(
                exception,
                firstError.getDefaultMessage(),
                BAD_REQUEST
        );
    }


    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<CommonResponseDto> serviceException(ServiceException exception) {
        return makeResponse(
                exception,
                exception.getMessage(),
                NOT_ACCEPTABLE
        );
    }


    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<CommonResponseDto> runtimeException(RuntimeException exception) {
        return makeResponse(
                exception,
                exception.getMessage(),
                INTERNAL_SERVER_ERROR
        );
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonResponseDto> otherException(Exception exception) {
        return makeResponse(
                exception,
                exception.getMessage(),
                INTERNAL_SERVER_ERROR
        );
    }


    private ResponseEntity<CommonResponseDto> makeResponse(
            Exception exception,
            String errorMessage,
            HttpStatus responseStatus
    ) {
        log.warn("Exception occured: ", exception);

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        return new ResponseEntity<>(
                CommonResponseDto.error(errorMessage),
                httpHeaders,
                responseStatus
        );
    }

}
