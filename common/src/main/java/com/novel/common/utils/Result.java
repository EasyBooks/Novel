/*
 * 作者：刘时明
 * 时间：2020/3/1-9:03
 * 作用：
 */
package com.novel.common.utils;

import lombok.Data;

@Data
public class Result
{
    private int code;
    private String info;

    public static Result of(int code,String info)
    {
        Result result=new Result();
        result.code=code;
        result.info=info;
        return result;
    }
}
