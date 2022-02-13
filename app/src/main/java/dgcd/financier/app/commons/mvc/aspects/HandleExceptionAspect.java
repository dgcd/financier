package dgcd.financier.app.commons.mvc.aspects;

import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.commons.mvc.dto.ResponseCode;
import dgcd.financier.app.commons.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
            return new GeneralResponseDto(ResponseCode.SERVICE_ERROR, e.getMessage());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return new GeneralResponseDto(ResponseCode.SERVER_ERROR, e.getMessage());
        }
    }

}
