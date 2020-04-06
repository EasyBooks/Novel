package com.novel.im.netty.handler.ws;

import com.google.gson.Gson;
import com.novel.common.bean.Snowflake;
import com.novel.common.domain.im.Message;
import com.novel.common.utils.DateUtil;
import com.novel.im.netty.handler.OnLineListService;
import com.novel.im.netty.wapper.json.JsonStrategyContext;
import com.novel.im.service.MsgService;
import com.novel.im.utils.MsgUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 10:59
 **/
@Component
@Slf4j
public class WebSocketRequestHandler
{
    private static final Gson gson = new Gson();
    @Autowired
    private JsonStrategyContext context;
    @Autowired
    private OnLineListService onLineListService;
    @Autowired
    private MsgService msgService;
    @Autowired
    private Snowflake snowflake;

    public void service(String req, ChannelHandlerContext ctx)
    {
        log.info("service收到消息，type=>{}", req);
        Message msg = MsgUtil.serializeMsg(req);
        if (msg == null)
        {
            ctx.writeAndFlush(new TextWebSocketFrame(String.format("echoes:%s", req)));
            return;
        }
        JsonStrategyContext.JsonResult rsp = context.service(msg);
        if (rsp == null)
        {
            // 没有回复处理
            return;
        }
        switch (rsp.getCmd())
        {
            case 2:
                if (rsp.isError())
                {
                    rsp.setResult("握手失败,错误的用户信息");
                } else
                {
                    String[] resultArr = rsp.getResult().split("[$]");
                    // 握手完成，保存信息
                    onLineListService.onLine(ctx, Integer.parseInt(resultArr[0]));
                    rsp.setResult("握手完成," + resultArr[1]);
                }
                break;
            case 4:
                if (rsp.isError())
                {
                    rsp.setResult("消息发送失败");
                } else
                {
                    rsp.setResult("消息发送成功");
                }
                break;
            default:
                break;
        }
        ctx.writeAndFlush(new TextWebSocketFrame(gson.toJson(rsp)));
    }

    public void mqService(Message msg)
    {
        log.info("mqService收到消息，msg=>{}", msg);
        OnLineListService.UserBean userBean;
        if (msg == null)
        {
            log.error("MQ流转消息解析失败");
            return;
        }
        switch (msg.getCmd())
        {
            // 私聊
            case 3:
                userBean = onLineListService.getUser(msg.getToId());
                if (userBean != null)
                {
                    msg.setCmd(4);
                    transFor(msg);
                    userBean.getChannel().writeAndFlush(new TextWebSocketFrame(gson.toJson(msg)));
                    msgService.saveMsg(msg);
                }else
                {
                    log.info("ID未命中");
                }
                break;
            // 群发
            case 5:
                // nop
                break;
            // 系统消息
            case 7:
                userBean = onLineListService.getUser(msg.getToId());
                if (userBean != null)
                {
                    msg.setCmd(8);
                    msg.setFormId(0);
                    transFor(msg);
                    userBean.getChannel().writeAndFlush(new TextWebSocketFrame(gson.toJson(msg)));
                    msgService.saveMsg(msg);
                }
                break;
            // 系统广播
            case 9:
                msg.setCmd(10);
                onLineListService.foreachSend(e -> e.writeAndFlush(gson.toJson(msg)));
                break;
        }
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
