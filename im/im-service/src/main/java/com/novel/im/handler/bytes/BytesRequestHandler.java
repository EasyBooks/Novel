/*
 * 作者：刘时明
 * 时间：2020/3/21-11:09
 * 作用：
 */
package com.novel.im.handler.bytes;

import com.novel.im.handler.HandlerContext;
import com.novel.im.handler.OnLineListService;
import com.novel.im.proto.DataProto;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BytesRequestHandler
{
    private static HandlerContext context;
    private static OnLineListService onLineListService;

    static
    {
        context = HandlerContext.getInstance();
        onLineListService = OnLineListService.getInstance();
    }

    public void service(DataProto.MsgReq req, ChannelHandlerContext ctx)
    {
        log.info("收到消息，type=>{}", req.getMsgType());
        DataProto.MsgRsp rsp = context.service(req);
        if (rsp == null)
        {
            // 没有回复处理
            return;
        }
        switch (req.getMsgType())
        {
            case SEND_MSG:
                OnLineListService.UserBean userBean = onLineListService.getUser(req.getSendMsgReq().getToId());
                userBean.getChannel().writeAndFlush(rsp.toByteArray());
                break;
            case HANDSHAKE_MSG:
                Long id = Long.parseLong(req.getHandShakeReq().getToken());
                onLineListService.onLine(ctx, id);
                ctx.writeAndFlush(Unpooled.copiedBuffer(rsp.toByteArray()));
                break;
            default:
                ctx.writeAndFlush(Unpooled.copiedBuffer(rsp.toByteArray()));
                break;
        }
    }
}
