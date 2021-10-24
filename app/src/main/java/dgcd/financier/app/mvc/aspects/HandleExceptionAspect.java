package dgcd.financier.app.mvc.aspects;

import dgcd.financier.app.mvc.dto.GeneralResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static dgcd.financier.app.mvc.dto.ResponseCode.ERROR;

@Slf4j
@Aspect
@Component
public class HandleExceptionAspect {

    @Around("@annotation(HandleException)")
    public GeneralResponseDto handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (GeneralResponseDto) joinPoint.proceed();
        } catch (Exception e) {
            log.error("Exception: ", e);
            return new GeneralResponseDto(ERROR, e.getMessage());
        }
    }

}
