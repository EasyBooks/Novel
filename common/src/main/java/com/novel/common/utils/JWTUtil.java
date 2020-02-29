/**
 * 作者：刘时明
 * 时间：2019/11/2-16:19
 * 作用：
 */
package com.novel.common.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JWTUtil
{
    private static final String subject = "root";
    private static final String secret = "novel.project";
    private static final long outMillis = 2 * 60 * 60 * 1000;

    /**
     * 根据uid获取token
     *
     * @param uid
     * @return
     */
    public static String createJWT(Integer uid)
    {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + outMillis))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .claim("uid", uid);
        return builder.compact();
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Integer parseJWT(String token)
    {
        try
        {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return (Integer) claims.get("uid");
        } catch (ExpiredJwtException e)
        {
            // 过期返回-1
            return -1;
        } catch (Exception e)
        {
            // 解析失败返回null
            return null;
        }
    }
}
