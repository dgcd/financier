package dgcd.financier.infrastructure.aspects;

import dgcd.financier.infrastructure.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Slf4j
@Aspect
@Component
public class LogControllerDataAspect {

    @Around("@annotation(LogControllerData)")
    public Object logControllerData(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = (MethodSignature) joinPoint.getSignature();
        var annotation = signature.getMethod().getAnnotation(LogControllerData.class);
        var methodName = signature.getName();

        logBefore(annotation.logParams(), methodName, joinPoint.getArgs());

        var startMillis = System.currentTimeMillis();
        var result = joinPoint.proceed();
        var millis = System.currentTimeMillis() - startMillis;

        logAfter(annotation.logResult(), methodName, result, millis);

        return result;
    }


    private void logBefore(
            boolean logParams,
            String methodName,
            Object[] args
    ) {
        if (logParams && args.length > 0) {
            var argsString = Arrays.toString(args);
            log.info(">>> Method {} is called with params: {}", methodName, argsString);
        } else {
            log.info(">>> Method {} is called ", methodName);
        }
    }


    private void logAfter(
            boolean logResult,
            String methodName,
            Object result,
            long millis
    ) {
        if (logResult || (result instanceof CommonResponseDto dto && nonNull(dto.errorMessage()))) {
            log.info("<<< Method {} finished in {} ms with result: {}", methodName, millis, result);
        } else {
            log.info("<<< Method {} finished in {} ms", methodName, millis);
        }
    }

}
