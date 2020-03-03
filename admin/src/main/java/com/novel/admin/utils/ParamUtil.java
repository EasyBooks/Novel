/*
 * 作者：刘时明
 * 时间：2020/3/3-23:02
 * 作用：
 */
package com.novel.admin.utils;

public class ParamUtil
{
    public static Integer[] PageVerify(Integer page, Integer size)
    {
        if (page == null || page < 0)
        {
            page = 0;
        }
        if (size == null || size <= 0 || size > 20)
        {
            size = 10;
        }
        return new Integer[]{page, size};
    }
}
