package com.example.springbootexperiment04.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@Aspect
public class MyAspect {
    @Around("execution(* com.example..*.buy*(..))")
    public Object calculateExecutionTime(ProceedingJoinPoint joinpoint) throws Throwable {
        long start = System.nanoTime();
        Object result = joinpoint.proceed();
        long end = System.nanoTime();
        log.debug("方法：{}()，的执行时间：{}", joinpoint.getSignature().getName(), end - start);
        return result;
    }

    @Around("@within(myInterceptor) || @annotation(myInterceptor)")
    public Object interecptorTarget(ProceedingJoinPoint joinpoint, MyInterceptor myInterceptor) throws Throwable {
        /*
        // 基于lambda实现
        Optional.ofNullable(myInterceptor)
                .or(() -> {
                    MyInterceptor m =
                            joinpoint.getTarget().getClass().getAnnotation(MyInterceptor.class);
                    return Optional.of(m);
                })
                .ifPresent(m -> {
                    for (MyInterceptor.AuthorityType t : m.value()) {
                        log.debug("当前执行方法的权限：{}", t);
                    }
                });
        */
        // 基于普通方法实现
        if (myInterceptor == null) {
            myInterceptor = joinpoint.getTarget().getClass().getAnnotation(MyInterceptor.class);
        }
        for (MyInterceptor.AuthorityType t : myInterceptor.value()) {
            log.debug("当前执行方法的权限：{}", t);
        }
        return joinpoint.proceed();
    }
}
