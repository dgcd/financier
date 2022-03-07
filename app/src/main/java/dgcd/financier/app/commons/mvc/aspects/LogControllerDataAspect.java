package dgcd.financier.app.commons.mvc.aspects;

import dgcd.financier.app.commons.mvc.dto.GeneralResponseDto;
import dgcd.financier.app.commons.mvc.dto.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogControllerDataAspect {

    @Around("@annotation(LogControllerData)")
    public Object logControllerData(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = (MethodSignature) joinPoint.getSignature();
        var annotation = signature.getMethod().getAnnotation(LogControllerData.class);
        var methodName = signature.getName();

        logBefore(annotation, methodName, joinPoint);

        var startMillis = System.currentTimeMillis();
        var result = joinPoint.proceed();
        var millis = System.currentTimeMillis() - startMillis;

        logAfter(annotation, methodName, result, millis);

        return result;
    }


    private void logBefore(
            LogControllerData annotation,
            String methodName,
            ProceedingJoinPoint joinPoint
    ) {
        if (annotation.logParams()) {
            var args = Arrays.toString(joinPoint.getArgs());
            log.info(">>> Method {} is called with params: {}", methodName, args);
        } else {
            log.info(">>> Method {} is called ", methodName);
        }
    }


    private void logAfter(
            LogControllerData annotation,
            String methodName,
            Object result,
            long millis
    ) {
        if (annotation.logResult() || (result instanceof GeneralResponseDto dto && !ResponseCode.OK.equals(dto.code()))) {
            log.info("<<< Method {} finished in {} ms with result: {}", methodName, millis, result);
        } else {
            log.info("<<< Method {} finished in {} ms", methodName, millis);
        }
    }

}
