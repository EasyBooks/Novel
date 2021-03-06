/**
 * 作者：刘时明
 * 时间：2019/11/2-16:19
 * 作用：
 */
package com.novel.common.utils;

import com.novel.common.define.AuthType;
import com.novel.common.define.Define;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtil
{
    private static final Map<Integer,AuthType> authMap;

    static
    {
        authMap=new HashMap<>(4);
        authMap.put(1,AuthType.USER);
        authMap.put(2,AuthType.AUTHOR);
        authMap.put(3,AuthType.ADMIN);
    }

    /**
     * 根据uid获取token
     *
     * @param uid
     * @return
     */
    public static String createJWT(Integer uid, Integer authType)
    {

        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(Define.SUBJECT)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Define.OUT_MILLIS))
                .signWith(SignatureAlgorithm.HS256, Define.SECRET.getBytes())
                .claim("uid", uid)
                .claim("authType", authMap.get(authType));
        return builder.compact();
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Claims parseJWT(String token)
    {
        try
        {
            return Jwts.parser()
                    .setSigningKey(Define.SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e)
        {
            // 过期
            Claims claims = new DefaultClaims();
            claims.put("error", "过期");
            return claims;
        } catch (Exception e)
        {
            // 解析失败
            Claims claims = new DefaultClaims();
            claims.put("error", "解析失败");
            return claims;
        }
    }
}
