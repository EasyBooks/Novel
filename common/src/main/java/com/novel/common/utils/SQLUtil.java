/*
 * 作者：刘时明
 * 时间：2020/4/14-22:56
 * 作用：
 */
package com.novel.common.utils;

import java.util.Map;

public class SQLUtil
{
    public static void limitAppend(StringBuilder sql, Map<String, Object> conditionMap)
    {
        if(conditionMap.containsKey("page")&&conditionMap.containsKey("size"))
        {
            sql.append(" LIMIT ").append(conditionMap.get("page")).append(",").append(conditionMap.get("size"));
        }
    }
}
