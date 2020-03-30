package com.novel.common.utils;

import javax.servlet.http.HttpServletRequest;

public class MetadataUtil
{
    public static String getVersions(HttpServletRequest request)
    {
        return request.getHeader("versions");
    }

    public static Integer getUserId(HttpServletRequest request)
    {
        String uid = request.getHeader("uid");
        return uid == null ? null : Integer.parseInt(uid);
    }

    public static String getDevice(HttpServletRequest request)
    {
        return request.getHeader("device");
    }
}
