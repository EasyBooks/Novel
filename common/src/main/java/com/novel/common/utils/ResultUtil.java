/**
 * 作者：刘时明
 * 时间：2019/11/2-17:34
 * 作用：
 */
package com.novel.common.utils;

import com.google.gson.Gson;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil
{
    public static final Gson gson = new Gson();
    public static final String limitResult;
    public static final String timeOutResult;
    public static final String errorResult;
    public static final String metadataResult;

    static
    {
        Result error = Result.of(10000,"错误请求");
        errorResult = gson.toJson(error);
        Result limit = Result.of(10001,"服务器繁忙，稍后再试");
        limitResult = gson.toJson(limit);
        Result timeOut = Result.of(10002,"认证已经过期，请重新登录");
        timeOutResult = gson.toJson(timeOut);
        Result metadata = Result.of(10003,"缺少请求元数据");
        metadataResult = gson.toJson(metadata);
    }

    public static Map<String, Object> success(String info)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("info", info);
        return result;
    }

    public static Map<String, Object> success(Object data)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", data);
        return result;
    }

    public static Map<String, Object> success(int code, String info)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("info", info);
        return result;
    }

    public static Map<String, Object> success(int code, Object data)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("data", data);
        return result;
    }

    public static Map<String, Object> success(int code, String info, Object data)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("info", info);
        result.put("data", data);
        return result;
    }

    public static Map<String, Object> error(Object data)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1000);
        result.put("data", data);
        return result;
    }

    public static Map<String, Object> error(String info)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1000);
        result.put("info", info);
        return result;
    }

    public static Map<String, Object> error(int code, Object data)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("data", data);
        return result;
    }

    public static Map<String, Object> result(int code, String info, Object data)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("info", info);
        result.put("data", data);
        return result;
    }
}
