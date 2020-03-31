/*
 * 作者：刘时明
 * 时间：2020/3/20-22:15
 * 作用：
 */
package com.novel.im.handler.bytes;

import com.novel.im.handler.OnLineListService;
import com.novel.im.proto.DataProto;
import com.novel.im.utils.ProtoBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BytesHandler extends ChannelInboundHandlerAdapter
{
    private static BytesRequestHandler handler;
    private static OnLineListService onLineListService;

    static
    {
        onLineListService=OnLineListService.getInstance();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(0, bytes);
        buf.release();

        DataProto.MsgReq req = ProtoBufUtil.parseReqBytes(bytes);
        if (req != null)
        {
            handler.service(req, ctx);
        } else
        {
            String str = "echos => " + new String(bytes);
            ctx.writeAndFlush(Unpooled.copiedBuffer(str.getBytes()));
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        onLineListService.leave(ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        log.debug("一名用户断开连接,name={}", ctx.name());
    }
}
