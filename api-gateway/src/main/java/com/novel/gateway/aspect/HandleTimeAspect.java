/*
 * 作者：刘时明
 * 时间：2020/3/29-12:34
 * 作用：
 */
package com.novel.gateway.aspect;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class HandleTimeAspect
{
    // @Around("execution(public * com.novel.gateway.handler.*.*(..))")
    public Object timeHandler(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Object object;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return null;
        HttpServletRequest request = attributes.getRequest();
        // Stopwatch stopwatch = Stopwatch.createStarted();
        try{
            long start=System.currentTimeMillis();
            object = joinPoint.proceed();
            long end=System.currentTimeMillis();
            log.info("接口:{},耗时:{}",request.getRequestURI(),end-start);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
        // log.info("接口:{},耗时:{}",request.getRequestURI(),stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return object;
    }
}
