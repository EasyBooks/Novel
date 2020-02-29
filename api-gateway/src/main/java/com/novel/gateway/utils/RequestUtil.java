/*
 * 作者：刘时明
 * 时间：2019/11/2-23:34
 * 作用：
 */
package com.novel.gateway.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil
{
    /**
     * 获取当前请求的IP,对使用代理后都有效
     *
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
