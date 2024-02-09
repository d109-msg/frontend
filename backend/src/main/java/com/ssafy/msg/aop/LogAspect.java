package com.ssafy.msg.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Profile("local")
@Component
public class LogAspect {

    @Pointcut("execution(* com.ssafy.msg..*(..))")
    public void all() {}

    @Pointcut("execution(* com.ssafy.msg..*Controller.*(..))")
    public void controller() {}

    @Pointcut("execution(* com.ssafy.msg..*ServiceImpl.*(..))")
    public void service() {}

    @Pointcut("execution(* com.ssafy.msg..*Repository.*(..))")
    public void repository() {}

    @Pointcut("execution(* com.ssafy.msg..*Mapper.*(..))")
    public void mapper() {}

    @Pointcut("execution(* com.ssafy.msg..*Util.*(..))")
    public void util() {}

    @Around("all()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
//        log.info("=======================< AROUND BEFORE >========================");

        Object result = null;

        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("=======================< AROUND AFTER >========================");

            log.info("method = {}", joinPoint.getSignature());
            log.info("result = {}", result);
            log.info("time = {} ms", timeMs);
        }
    }

    @Before("controller() || service()")
    public void beforeLogic(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("=======================< BEFORE >==============================");

        log.info("method = {}", joinPoint.getSignature());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg != null) {
                log.info("type = {}", arg.getClass().getSimpleName());
                log.info("value = {}", arg);
            }

        }
    }

    @After("controller() || service()")
    public void afterLogic(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("=======================< AFTER >===============================");
        log.info("method = {}", joinPoint.getSignature());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg != null) {
                log.info("type = {}", arg.getClass().getSimpleName());
                log.info("value = {}", arg);
            }

        }
    }
}
