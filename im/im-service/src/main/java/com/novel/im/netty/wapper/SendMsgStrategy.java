/*
 * 作者：刘时明
 * 时间：2020/3/21-14:21
 * 作用：
 */
package com.novel.im.netty.wapper;

import com.novel.common.domain.im.Message;
import com.novel.im.nsq.MsgProducer;
import com.novel.im.proto.DataProto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMsgStrategy implements HandlerStrategy<DataProto.SendMsgReq>
{
    private static final DataProto.MsgType TYPE = DataProto.MsgType.SEND_MSG;

    @Autowired
    private MsgProducer producer;

    @Override
    public DataProto.MsgRsp protoBufHandler(DataProto.SendMsgReq req)
    {
        //        String formAesKey = userDao.findAesKey(req.getFormId());
//        String ToAesKey = userDao.findAesKey(req.getToId());
        Message msg = new Message();
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

    @Override
    public String jsonHandler(Message msg)
    {
        producer.produce(msg);
        System.out.println("消息发送");
        return "";
    }
}
