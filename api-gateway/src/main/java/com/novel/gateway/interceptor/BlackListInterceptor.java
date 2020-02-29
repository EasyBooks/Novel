/*
 * 作者：刘时明
 * 时间：2019/11/2-21:37
 * 作用：
 */
package com.novel.gateway.interceptor;

import com.novel.common.utils.ResultUtil;
import com.novel.gateway.logic.AddersLogic;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlackListInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (AddersLogic.isBlackList(request.getRemoteAddr()))
        {
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().println(ResultUtil.errorResult);
            return false;
        }
        return true;
    }
}
