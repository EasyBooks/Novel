/*
 * 作者：刘时明
 * 时间：2020/3/21-14:21
 * 作用：
 */
package com.novel.im.handler.strategy;

import com.novel.common.domain.im.Msg;
import com.novel.im.handler.HandlerStrategy;
import com.novel.im.proto.DataProto;
import org.springframework.beans.BeanUtils;

public class SendMsgStrategy implements HandlerStrategy<DataProto.SendMsgReq>
{
    private static final DataProto.MsgType TYPE = DataProto.MsgType.SEND_MSG;

    @Override
    public DataProto.MsgRsp handler(DataProto.SendMsgReq req)
    {
//        String formAesKey = userDao.findAesKey(req.getFormId());
//        String ToAesKey = userDao.findAesKey(req.getToId());
        Msg msg = new Msg();
        BeanUtils.copyProperties(req, msg);
        // msg.setId(snowflake.nextId());
        //MsgUtil.decryptMsg(msg, formAesKey);
        // msgDao.save(msg);
        //MsgUtil.encryptMsg(msg, ToAesKey);
        DataProto.SendMsgRsp rsp = DataProto.SendMsgRsp.newBuilder()
                .setContent(msg.getContent())
                .setFormId(req.getFormId())
                .setToId(req.getToId())
                .setType(req.getType())
                .setTimeStamp(req.getTimeStamp())
                .setId(msg.getId())
                .build();
        return DataProto.MsgRsp.newBuilder()
                .setMsgType(TYPE)
                .setSendMsgRsp(rsp)
                .build();
    }
}
