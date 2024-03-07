package com.example.default1.component;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class GlobalLogAop {
    @Pointcut("execution(* com.example.default1..controller..*(..))")
    private void pointCut() {
    }


    @Around("pointCut()")
    public Object LogInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();    // 실행된 메서드의 클래스 이름
        String methodName = joinPoint.getSignature().getName();                 // 실행된 메서드 이름
        String params = Arrays.toString(joinPoint.getArgs());                   // 실행된 메서드의 파라미터

        log.info("{}.{}: {}", className, methodName, params);

        Object retVal = joinPoint.proceed();                                    // 실제 메서드 시작
        return retVal;
    }
}
