/**
 * 作者：刘时明
 * 时间：2019/11/2-16:36
 * 作用：
 */
package com.novel.gateway.utils;


import com.novel.common.utils.JWTUtil;

public class AuthUtil
{
    public static AuthVerifyType VerifyToken(String token, String uid)
    {
        if (token == null || uid == null)
        {
            return AuthVerifyType.VERIFY_ERR;
        }
        Integer parseUid = JWTUtil.parseJWT(token);
        System.out.println("parseUid="+parseUid);
        if (parseUid == null)
        {
            return AuthVerifyType.VERIFY_ERR;
        }
        if (parseUid.compareTo(0) < 0)
        {
            return AuthVerifyType.VERIFY_TIME_OUT;
        }
        if ((parseUid + "").equals(uid))
        {
            return AuthVerifyType.VERIFY_OK;
        } else
        {
            return AuthVerifyType.VERIFY_ERR;
        }
    }
}
