/*
 * 作者：刘时明
 * 时间：2020/3/28-19:53
 * 作用：
 */
package com.novel.gateway.aspect;

import com.novel.gateway.aspect.annotation.IdParam;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

@Component
@Aspect
@Slf4j
public class ParamAndTimeAspect
{
    /**
     * 从请求头取出uid，放置到参数列表，并统计接口运行时间
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("execution(public * com.novel.gateway.handler.*.*(..))")
    public Object AroundExec(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] annotations = methodSignature.getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        for (int i = 0; i < annotations.length; i++)
        {
            for (int j = 0; j < annotations[i].length; j++)
            {
                if (annotations[i][j].annotationType() == IdParam.class)
                {
                    String uid = request.getHeader("uid");
                    if (uid == null) break;
                    args[i] = Integer.parseInt(uid);
                }
            }
        }
        long start=System.currentTimeMillis();
        Object result = joinPoint.proceed(args);
        long end=System.currentTimeMillis();
        log.info("接口:{},耗时:{} ms",request.getRequestURI(),end-start);
        return result;
    }
}
