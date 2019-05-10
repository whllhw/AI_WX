package xyz.whllhw.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerLoggingAop {
    private final Logger logger = LoggerFactory.getLogger(TimerLoggingAop.class);

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            long stop = System.currentTimeMillis();
            logger.info("{} method cast time = {}ms", pjp.getSignature().getName(), (stop - start));
        }
    }
}
