/*
 * 作者：刘时明
 * 时间：2020/3/21-14:04
 * 作用：
 */
package com.novel.im.netty.handler.wapper.proto;

import com.novel.im.netty.handler.wapper.AckStrategy;
import com.novel.im.netty.handler.wapper.HandShakeStrategy;
import com.novel.im.netty.handler.wapper.SendMsgStrategy;
import com.novel.im.proto.DataProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProtoBufStrategyContext
{
    @Autowired
    private AckStrategy ackStrategy;
    @Autowired
    private HandShakeStrategy handShakeStrategy;
    @Autowired
    private SendMsgStrategy sendMsgStrategy;

    public DataProto.MsgRsp service(DataProto.MsgReq req)
    {
        switch (req.getMsgType())
        {
            case ACK_MSG:
                return ackStrategy.protoBufHandler(null);
            case HANDSHAKE_MSG:
                return handShakeStrategy.protoBufHandler(req.getHandShakeReq());
            case PONG_MSG:
            case FILE_MSG:
            case SYSTEM_MSG:
            case SYSTEM_BROADCAST_MSG:
            case LEAVE_MSG:
            case SEND_MSG:
                return sendMsgStrategy.protoBufHandler(req.getSendMsgReq());
            default:
                return null;
        }
    }
}
