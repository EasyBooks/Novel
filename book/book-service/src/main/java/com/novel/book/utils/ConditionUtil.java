/*
 * 作者：刘时明
 * 时间：2020/1/19-19:46
 * 作用：
 */
package com.novel.book.utils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class ConditionUtil
{
    public static Map<String,Object> getBookCondition(Map<String,Object> conditionMap)
    {
        // type 1书架，2收藏
        // uid 用户ID，查询用户相关的
        return conditionMap;
    }

    public static Map<String,Object> getTypeCondition(Map<String,Object> conditionMap)
    {
        return conditionMap;
    }

    public static <T> Wrapper<T> getWrapperByMap(Map<String,Object> conditionMap)
    {
        QueryWrapper<T> wrapper=new QueryWrapper<T>();
        return new QueryWrapper<>();
    }
}
