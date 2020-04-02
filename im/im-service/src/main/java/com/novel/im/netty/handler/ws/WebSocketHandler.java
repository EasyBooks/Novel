/*
 * 作者：刘时明
 * 时间：2020/3/20-21:13
 * 作用：
 */
package com.novel.im.netty.handler.ws;

import com.novel.im.netty.handler.AbstractHandler;
import com.novel.im.netty.handler.OnLineListService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketHandler extends AbstractHandler
{
    @Autowired
    private WebSocketRequestHandler handler;
    @Autowired
    private OnLineListService onLineListService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        // WS第一次需要使用http建立握手
        if (msg instanceof FullHttpRequest)
        {
            // nop
        }else if (msg instanceof TextWebSocketFrame)
        {
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            handler.service(frame.text(), ctx);
        }else
        {
            log.error("非法信息类型,type="+(msg==null?null:msg.getClass()));
        }
    }

    @Override
    public OnLineListService onLineListService()
    {
        return onLineListService;
    }
}
