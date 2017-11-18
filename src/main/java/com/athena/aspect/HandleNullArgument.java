package com.athena.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by Tommy on 2017/11/19.
 */
@Aspect
@Component
public class HandleNullArgument {


    @Around("@annotation(com.athena.annotation.ArgumentNotNull)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        for (Object object : arguments) {
            if (object == null) {
                System.out.println(joinPoint.getSignature().toShortString());
                throw new NullPointerException();
            }
        }
        return joinPoint.proceed();
    }
}
