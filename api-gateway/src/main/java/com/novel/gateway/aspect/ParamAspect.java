/*
 * 作者：刘时明
 * 时间：2020/3/28-19:53
 * 作用：
 */
package com.novel.gateway.aspect;

import com.novel.gateway.aspect.annotation.IdParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;

@Component
@Aspect
public class ParamAspect
{
    /**
     * 从请求头取出uid，放置到参数列表
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("execution(public * com.novel.gateway.handler.*.*(..))")
    public void BeforeExec(JoinPoint joinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] annotations = methodSignature.getMethod().getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++)
        {
            for (int j = 0; j < annotations[i].length; j++)
            {
                if (annotations[i][j].annotationType() == IdParam.class)
                {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if (attributes == null) return;
                    HttpServletRequest request = attributes.getRequest();
                    String uid = request.getHeader("uid");
                    if (uid == null) return;
                    Object[] args = joinPoint.getArgs();
                    args[i] = Integer.parseInt(uid);
                    ((ProceedingJoinPoint) joinPoint).proceed(args);
                }
            }
        }
    }
}
