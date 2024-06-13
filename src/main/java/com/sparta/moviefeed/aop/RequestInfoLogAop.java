package com.sparta.moviefeed.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "RequestInfoLogAop")
@Aspect
@Component
public class RequestInfoLogAop {

    @Pointcut("execution(* com.sparta.moviefeed.controller..*(..))")
    private void forAllController() {}

    @Around("forAllController()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String method = request.getMethod();
        String url = request.getRequestURI();

        try {
            // 핵심기능 수행
            Object output = joinPoint.proceed();
            return output;
        } finally {
            log.info("[RequestInfoLog] Method = {}, URL = {}", method, url);
        }
    }
}
