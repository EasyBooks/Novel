/*
 * 作者：刘时明
 * 时间：2020/3/21-14:04
 * 作用：
 */
package com.novel.im.netty.handler.wapper.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.novel.im.netty.handler.wapper.AckStrategy;
import com.novel.im.netty.handler.wapper.HandShakeStrategy;
import com.novel.im.netty.handler.wapper.SendMsgStrategy;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JsonStrategyContext
{
    @Autowired
    private AckStrategy ackStrategy;
    @Autowired
    private HandShakeStrategy handShakeStrategy;
    @Autowired
    private SendMsgStrategy sendMsgStrategy;

    public JsonResult service(String json)
    {
        int cmd;
        try
        {
            JsonObject jsonObject =JsonParser.parseString(json).getAsJsonObject();
            cmd = jsonObject.get("cmd").getAsInt();
        }catch (Exception e)
        {
            return JsonResult.of(0,"echoes:"+json);
        }
        switch (cmd)
        {
            case 0:
                ackStrategy.jsonHandler(json);
                return null;
            case 1:
                String uid=handShakeStrategy.jsonHandler(json);
                if(uid==null) return JsonResult.of(cmd+1,null);
                String publicKey=UUID.randomUUID().toString().substring(0,5);
                return JsonResult.of(cmd+1,publicKey);
            case 3:
            case 5:
            case 7:
            case 9:
            case 10:
            case 12:
            default:
                return null;
        }
    }

    @Data
    public static class JsonResult
    {
        int cmd;
        Object result;

        public JsonResult(int cmd, Object result)
        {
            this.cmd = cmd;
            this.result = result;
        }

        public static JsonResult of(int cmd)
        {
            return new JsonResult(cmd,null);
        }

        public static JsonResult of(int cmd,Object result)
        {
            return new JsonResult(cmd,result);
        }
    }
}
