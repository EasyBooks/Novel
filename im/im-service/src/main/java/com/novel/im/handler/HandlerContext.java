/*
 * 作者：刘时明
 * 时间：2020/3/21-14:04
 * 作用：
 */
package com.novel.im.handler;

import com.novel.im.handler.strategy.AckStrategy;
import com.novel.im.handler.strategy.HandShakeStrategy;
import com.novel.im.handler.strategy.SendMsgStrategy;
import com.novel.im.proto.DataProto;

public class HandlerContext
{
    private static final HandlerContext handlerContext=new HandlerContext();

    private AckStrategy ackStrategy;
    private HandShakeStrategy handShakeStrategy;
    private SendMsgStrategy sendMsgStrategy;

    {
        ackStrategy = new AckStrategy();
        handShakeStrategy = new HandShakeStrategy();
        sendMsgStrategy = new SendMsgStrategy();
    }

    public static HandlerContext getInstance()
    {
        return handlerContext;
    }

    public DataProto.MsgRsp service(DataProto.MsgReq req)
    {
        switch (req.getMsgType())
        {
            case ACK_MSG:
                return ackStrategy.handler(null);
            case HANDSHAKE_MSG:
                return handShakeStrategy.handler(req.getHandShakeReq());
            case PONG_MSG:
            case FILE_MSG:
            case SYSTEM_MSG:
            case SYSTEM_BROADCAST_MSG:
            case LEAVE_MSG:
            case SEND_MSG:
                return sendMsgStrategy.handler(req.getSendMsgReq());
            default:
                return null;
        }
    }
}
