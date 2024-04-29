package dgcd.financier.infra.gateway.exception;

import dgcd.financier.infra.gateway.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResponseDto> validationException(MethodArgumentNotValidException exception) {
        var message = exception.getBindingResult()
                .getAllErrors()
                .getFirst()
                .getDefaultMessage();
        return makeResponse(exception, message, BAD_REQUEST);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<CommonResponseDto> illegalArgumentException(IllegalArgumentException exception) {
        return makeResponse(exception, BAD_REQUEST);
    }


    // https://github.com/spring-projects/spring-framework/issues/31569
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(NoResourceFoundException ex) {
        throw new IllegalStateException();
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonResponseDto> otherException(Exception exception) {
        return makeResponse(exception, INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<CommonResponseDto> makeResponse(Exception exception, HttpStatus status) {
        return makeResponse(exception, exception.getMessage(), status);
    }


    private ResponseEntity<CommonResponseDto> makeResponse(
            Exception exception,
            String errorMessage,
            HttpStatus status
    ) {
        log(exception);

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        var dto = CommonResponseDto.error(errorMessage);

        return new ResponseEntity<>(dto, httpHeaders, status);
    }


    private static void log(Exception exception) {
        if (log.isDebugEnabled()) {
            log.warn("Exception occurred: ", exception);
        } else {
            log.warn("Exception occurred: {}", exception.getMessage());
        }
    }

}
