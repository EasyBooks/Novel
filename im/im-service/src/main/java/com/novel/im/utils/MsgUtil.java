/*
 * 作者：刘时明
 * 时间：2020/1/9-23:51
 * 作用：
 */
package com.novel.im.utils;

import com.google.gson.Gson;
import com.novel.common.domain.im.Msg;

public class MsgUtil
{
    private static final Gson gson = new Gson();

    public static Msg strToMsg(String msg)
    {
        return gson.fromJson(msg, Msg.class);
    }

    public static String msgToStr(Msg msg)
    {
        return gson.toJson(msg);
    }
}
