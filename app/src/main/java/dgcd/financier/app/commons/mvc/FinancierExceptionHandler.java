package dgcd.financier.app.commons.mvc;

import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Slf4j
@ControllerAdvice
public class FinancierExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<GeneralResponseDto> validationException(MethodArgumentNotValidException exception) {
        var error = exception.getBindingResult().getAllErrors().iterator().next();
        log.warn("validationException: {}", error.getDefaultMessage());
        return getGeneralResponseDtoResponseEntity(error.getDefaultMessage(), BAD_REQUEST);
    }


    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<GeneralResponseDto> runtimeException(RuntimeException exception) {
        log.info("runtimeException {}", exception.getMessage());
        return getGeneralResponseDtoResponseEntity(exception.getMessage(), NOT_ACCEPTABLE);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<GeneralResponseDto> otherException(Exception exception) {
        log.info("otherException {}", exception.getMessage());
        return getGeneralResponseDtoResponseEntity(exception.getMessage(), INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<GeneralResponseDto> getGeneralResponseDtoResponseEntity(
            String body,
            HttpStatus status
    ) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(
                new GeneralResponseDto(null, body),
                httpHeaders,
                status
        );
    }

}
