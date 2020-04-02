package com.novel.gateway.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 18:52
 **/
public class ResponseUtil
{
    private static final String JSON_CONTENT_TYPE="application/json; charset=utf-8";

    public static void json(HttpServletResponse response,String json) throws IOException
    {
        response.setContentType(JSON_CONTENT_TYPE);
        response.getWriter().print(json);
    }
}
