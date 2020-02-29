/**
 * 作者：刘时明
 * 时间：2019/11/5-19:23
 * 作用：
 */
package com.novel.gateway.aspect;

import com.novel.common.utils.ResultUtil;
import com.novel.gateway.handler.UserHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Aspect
public class RequestAspect
{
    // 访问人次
    private AtomicInteger requestCount = new AtomicInteger(0);
    // 访问人数
    private CopyOnWriteArraySet<String> requestUserSet = new CopyOnWriteArraySet<>();

    /**
     * 基于方法通配符的普通方法拦截
     *
     * @throws Throwable
     */
    @AfterReturning("execution(public * com.novel.gateway.handler.*.*(..))")
    public void requestHandler() throws Throwable
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uid = request.getHeader("uid");
        if (uid != null)
        {
            requestUserSet.add(uid);
        }
        System.out.println("请求路径：" + request.getRequestURI());
        System.out.println("请求人次=" + requestCount.incrementAndGet());
    }

    /**
     * 基于注解的连接点拦截
     *
     * @param joinPoint
     * @param result
     * @return
     * @throws Throwable
     */
    @AfterReturning(returning = "result", pointcut = "@annotation(com.novel.gateway.aspect.annotation.LogInterceptJoinPoint)")
    public Object AfterExec(JoinPoint joinPoint, Object result) throws Throwable
    {
        Class<?> clazz = joinPoint.getSignature().getDeclaringType();
        String methodName = joinPoint.getSignature().getName();
        Object code = null;
        if (result instanceof Map)
        {
            code = ((Map) result).get("code");
        }
        if (clazz == UserHandler.class && "login".equals(methodName))
        {
            if (code.equals(0))
            {
                System.out.println("登录成功");
            } else
            {
                System.out.println("登录失败");
            }
        } else if (clazz == UserHandler.class && "register".equals(methodName))
        {
            if (code.equals(0))
            {
                System.out.println("注册成功");
            } else
            {
                System.out.println("注册失败");
            }
        }
        System.out.println("result=" + result);
        return result;
    }
}
