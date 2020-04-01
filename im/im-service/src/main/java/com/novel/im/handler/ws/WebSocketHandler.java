/*
 * 作者：刘时明
 * 时间：2020/3/20-21:13
 * 作用：
 */
package com.novel.im.handler.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class WebSocketHandler extends ChannelInboundHandlerAdapter
{
    // 记录管理所有的客户端
    static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        // 传统http接入 第一次需要使用http建立握手
        if (msg instanceof FullHttpRequest)
        {
//            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
//            QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.uri());
//            decoder.parameters().entrySet().forEach(entry ->
//            {
//                System.out.println(entry.getKey() + "=" + entry.getValue().get(0));
//            });
            ctx.channel().write(new TextWebSocketFrame("连接成功"));
        }
        if (msg instanceof WebSocketFrame)
        {
            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
            msgHandler(ctx, webSocketFrame);
        }
    }

    private void msgHandler(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception
    {
        String message = frame.content().toString(CharsetUtil.UTF_8);
        log.info("接受的消息={}", message);
        ctx.writeAndFlush(new TextWebSocketFrame("hello"));
        //ctx.writeAndFlush(new BinaryWebSocketFrame(buf.writeBytes(array)));
    }

    // 用户加入
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        clients.add(ctx.channel());
        log.info("一个用户加入，当前={}", clients.size());
    }

    // 用户离开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        clients.remove(ctx.channel());
        log.info("一个用户离开，当前={}，id={}", clients.size(), ctx.channel().id().asShortText());
    }
}
