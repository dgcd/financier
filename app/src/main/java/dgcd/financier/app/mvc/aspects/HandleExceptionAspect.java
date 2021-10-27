package dgcd.financier.app.mvc.aspects;

import dgcd.financier.app.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static dgcd.financier.app.mvc.dto.ResponseCode.SERVER_ERROR;
import static dgcd.financier.app.mvc.dto.ResponseCode.SERVICE_ERROR;

@Slf4j
@Aspect
@Component
public class HandleExceptionAspect {

    @Around("@annotation(HandleException)")
    public GeneralResponseDto handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (GeneralResponseDto) joinPoint.proceed();
        } catch (ServiceException e) {
            log.warn("Exception: {}", e.getMessage());
            return new GeneralResponseDto(SERVICE_ERROR, e.getMessage());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return new GeneralResponseDto(SERVER_ERROR, e.getMessage());
        }
    }

}
