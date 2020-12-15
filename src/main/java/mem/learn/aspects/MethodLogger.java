package mem.learn.aspects;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLogger {
    private final Logger logger = LoggerFactory.getLogger(MethodLogger.class.getName());


    @Around("execution(public * *(..)) && @annotation(mem.learn.annotations.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = point.proceed();
            logger.info(String.format("#%s(%s): %s in %s[msec]",
                    MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
                    JSON.toJSONString(point.getArgs()),
                    JSON.toJSONString(result),
                    System.currentTimeMillis() - start));
            return result;
        } catch (Exception e) {
            logger.error(String.format("#%s(%s):",
                    MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
                    JSON.toJSONString(point.getArgs())), e);
            throw e;
        }

    }
}
