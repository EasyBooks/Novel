/**
 * 作者：刘时明
 * 时间：2019/11/2-16:36
 * 作用：
 */
package com.novel.common.utils;

import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

public class AuthUtil
{
    public static AuthVerifyType VerifyToken(String token, String uid)
    {
        return VerifyToken(token, uid, null);
    }

    public static AuthVerifyType VerifyToken(String token, String uid, HttpServletRequest request)
    {
        if (token == null || uid == null)
        {
            return AuthVerifyType.VERIFY_ERR;
        }
        Claims claims = JWTUtil.parseJWT(token);
        Object error = claims.get("error");
        if ("过期".equals(error))
        {
            return AuthVerifyType.VERIFY_TIME_OUT;
        } else if ("解析失败".equals(error))
        {
            return AuthVerifyType.VERIFY_ERR;
        }
        if ((claims.get("uid") + "").equals(uid))
        {
            if (request != null)
            {
                request.setAttribute("authType", claims.get("authType"));
            }
            return AuthVerifyType.VERIFY_OK;
        } else
        {
            return AuthVerifyType.VERIFY_ERR;
        }
    }
}
