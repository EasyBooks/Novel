/*
 * 作者：刘时明
 * 时间：2020/3/21-14:21
 * 作用：
 */
package com.novel.im.netty.wapper;

import com.google.gson.Gson;
import com.novel.common.bean.Snowflake;
import com.novel.common.domain.im.Message;
import com.novel.common.utils.DateUtil;
import com.novel.im.netty.handler.OnLineListService;
import com.novel.im.netty.handler.ws.WebSocketRequestHandler;
import com.novel.im.nsq.MsgProducer;
import com.novel.im.proto.DataProto;
import com.novel.im.service.MsgService;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMsgStrategy implements HandlerStrategy<DataProto.SendMsgReq>
{
    private static final DataProto.MsgType TYPE = DataProto.MsgType.SEND_MSG;
    private static final Gson gson=new Gson();

    @Autowired
    private MsgProducer producer;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private OnLineListService onLineListService;

    @Autowired
    private MsgService msgService;

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
        OnLineListService.UserBean userBean = onLineListService.getUser(msg.getToId());
        if(userBean!=null)
        {
            switch (msg.getCmd())
            {
                case 3:
                    msg.setCmd(4);
                    transFor(msg);
                    userBean.getChannel().writeAndFlush(new TextWebSocketFrame(gson.toJson(msg)));
                    msgService.saveMsg(msg);
                    break;
                case 7:

                    break;
                default:
                    producer.produce(msg);
                    break;
            }
        }else
        {
            producer.produce(msg);
        }
        return "";
    }

    private void theHandler(Message msg,OnLineListService.UserBean userBean)
    {
        msg.setCmd(4);
        transFor(msg);
        userBean.getChannel().writeAndFlush(new TextWebSocketFrame(gson.toJson(msg)));
        msgService.saveMsg(msg);
    }

    private void transFor(Message msg)
    {
        int now = DateUtil.nowTime();
        msg.setId(snowflake.nextId());
        msg.setStatus(1);
        msg.setCreateTime(now);
        msg.setUpdateTime(now);
    }
}
