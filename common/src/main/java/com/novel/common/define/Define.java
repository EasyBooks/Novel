/*
 * 作者：刘时明
 * 时间：2020/3/4-10:10
 * 作用：
 */
package com.novel.common.define;

import java.util.Map;

public class Define
{
    // 启用
    public static final int ENABLE = 1;
    // 禁用
    public static final int DISABLE = 0;

    // JWT 签名
    public static final String SUBJECT = "root";
    // JWT密钥
    public static final String SECRET = "novel.project";
    // JWT token有效时间
    public static final long OUT_MILLIS = 7 * 24 * 60 * 60 * 1000;

    public static Map<String, Object> conditionMap()
    {
        return Map.of("status", ENABLE);
    }

    public static final int ZONGHENG_PLATFORM = 1;
    public static final int JINYONG_PLATFORM = 2;
}
