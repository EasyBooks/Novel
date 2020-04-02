/*
 * 作者：刘时明
 * 时间：2020/3/20-22:15
 * 作用：
 */
package com.novel.im.netty.handler.bytes;

import com.novel.im.netty.handler.AbstractHandler;
import com.novel.im.netty.handler.OnLineListService;
import com.novel.im.proto.DataProto;
import com.novel.im.utils.ProtoBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BytesHandler extends AbstractHandler
{
    @Autowired
    private BytesRequestHandler handler;
    @Autowired
    private OnLineListService onLineListService;

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
    public OnLineListService onLineListService()
    {
        return onLineListService;
    }
}
