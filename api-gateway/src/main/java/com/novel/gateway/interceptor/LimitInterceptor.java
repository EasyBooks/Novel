/**
 * 作者：刘时明
 * 时间：2019/11/2-17:18
 * 作用：
 */
package com.novel.gateway.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.novel.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LimitInterceptor implements HandlerInterceptor
{
    // 每秒生成50个许可
    private static final RateLimiter rateLimiter = RateLimiter.create(50);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (rateLimiter.tryAcquire())
        {
            return true;
        } else
        {
            log.debug("请求被限流");
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().println(ResultUtil.limitResult);
            return false;
        }
    }
}
