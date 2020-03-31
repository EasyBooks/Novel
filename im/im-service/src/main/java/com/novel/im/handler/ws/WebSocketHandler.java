/*
 * 作者：刘时明
 * 时间：2020/3/20-21:13
 * 作用：
 */
package com.novel.im.handler.ws;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class WebSocketHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (msg instanceof WebSocketFrame)
        {
            msgHandler(ctx, (WebSocketFrame) msg);
        } else if (msg instanceof DefaultFullHttpRequest)
        {
            DefaultFullHttpRequest request = (DefaultFullHttpRequest) msg;
            byte[] bytes = request.content().array();
            System.out.println("字节数据="+Arrays.toString(bytes));
        } else
        {
            log.error("非法消息类型,class=" + (msg == null ? null : msg.getClass()));
        }
    }

    private void msgHandler(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception
    {
        byte[] array = frame.content().array();
//        if (msg == null)
//        {
//            ctx.close().sync();
//            log.warn("非法信息，连接断开");
//            return;
//        }
//        switch (msg.getCmd())
//        {
//            case 1:
//                handshake(msg.getFormId(), msg.getContent(), ctx);
//                break;
//            case 2:
//                privateSend(msg.getFormId(), msg.getToId(), msg.getContent(), ctx);
//                break;
//            case 3:
//                break;
//        }
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        ctx.writeAndFlush(new BinaryWebSocketFrame(buf.writeBytes(array)));
    }

    // 用户离开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
//        String id = ctx.channel().id().toString();
//        if (idMap.containsKey(id))
//        {
//            userBeanMap.remove(idMap.get(id));
//            idMap.remove(id);
//            log.info("一个用户离开，当前={}，id={}", userBeanMap.size(), id);
//        }
    }
}
