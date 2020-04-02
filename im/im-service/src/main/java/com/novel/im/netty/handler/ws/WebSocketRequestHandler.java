package com.novel.im.netty.handler.ws;

import com.google.gson.Gson;
import com.novel.im.netty.handler.OnLineListService;
import com.novel.im.netty.handler.wapper.json.JsonStrategyContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 10:59
 **/
@Component
@Slf4j
public class WebSocketRequestHandler
{
    private static final Gson gson=new Gson();
    @Autowired
    private JsonStrategyContext context;
    @Autowired
    private OnLineListService onLineListService;

    public void service(String req, ChannelHandlerContext ctx)
    {
        log.info("收到消息，type=>{}", req);
        JsonStrategyContext.JsonResult rsp = context.service(req);
        if (rsp == null)
        {
            // 没有回复处理
            return;
        }
        switch (rsp.getCmd())
        {
            case -1:

                break;
        }
        ctx.writeAndFlush(new TextWebSocketFrame(gson.toJson(rsp)));
    }

    public void mqService(String req)
    {
        log.info("收到消息，type=>{}", req);
        context.service(req);
    }
}
