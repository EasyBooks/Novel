/*
 * 作者：刘时明
 * 时间：2020/3/21-10:51
 * 作用：
 */
package com.novel.im.utils;

import com.google.gson.Gson;
import com.novel.common.domain.im.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgUtil
{
    private static final Gson gson=new Gson();

    public static void decryptMsg(Message msg, String aesKey)
    {
        try
        {
            String content = DESUtil.decrypt(msg.getContent(), aesKey);
            msg.setContent(content);
        } catch (Exception e)
        {
            log.error("解密失败，err={}", e.getMessage());
        }
    }

    public static void encryptMsg(Message msg, String aesKey)
    {
        try
        {
            String content = DESUtil.encrypt(msg.getContent(), aesKey);
            msg.setContent(content);
        } catch (Exception e)
        {
            log.error("解密失败，err={}", e.getMessage());
        }
    }

    public static Message serializeMsg(String json)
    {
        try
        {
//            JsonObject jsonObject =JsonParser.parseString(json).getAsJsonObject();
//            cmd = jsonObject.get("cmd").getAsInt();
            return gson.fromJson(json, Message.class);
        }catch (Exception e)
        {
            return null;
        }
    }
}
