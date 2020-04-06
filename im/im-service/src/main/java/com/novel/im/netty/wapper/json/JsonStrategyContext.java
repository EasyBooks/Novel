/*
 * 作者：刘时明
 * 时间：2020/3/21-14:04
 * 作用：
 */
package com.novel.im.netty.wapper.json;

import com.google.gson.Gson;
import com.novel.common.domain.im.Message;
import com.novel.im.netty.wapper.AckStrategy;
import com.novel.im.netty.wapper.HandShakeStrategy;
import com.novel.im.netty.wapper.SendMsgStrategy;
import com.novel.im.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonStrategyContext
{
    private final Gson gson = new Gson();

    @Autowired
    private UserService userService;

    @Autowired
    private AckStrategy ackStrategy;
    @Autowired
    private HandShakeStrategy handShakeStrategy;
    @Autowired
    private SendMsgStrategy sendMsgStrategy;

    public JsonResult service(Message msg)
    {
        int respCmd = msg.getCmd() + 1;
        switch (msg.getCmd())
        {
            case 0:
                ackStrategy.jsonHandler(msg);
                return null;
            case 1:
                return JsonResult.of(respCmd, handShakeStrategy.jsonHandler(msg));
            case 3:
                return JsonResult.of(respCmd, sendMsgStrategy.jsonHandler(msg));
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
        String result;

        public JsonResult(int cmd, String result)
        {
            this.cmd = cmd;
            this.result = result;
        }

        public boolean isError()
        {
            return this.result == null;
        }

        public static JsonResult of(int cmd)
        {
            return new JsonResult(cmd, null);
        }

        public static JsonResult of(int cmd, String result)
        {
            return new JsonResult(cmd, result);
        }
    }
}
