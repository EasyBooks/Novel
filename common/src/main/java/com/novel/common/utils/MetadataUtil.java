package com.novel.common.utils;

import javax.servlet.http.HttpServletRequest;

public class MetadataUtil
{
    public static String getVersions(HttpServletRequest request)
    {
        return request.getHeader("versions");
    }

    public static String getDevice(HttpServletRequest request)
    {
        return request.getHeader("device");
    }
}
