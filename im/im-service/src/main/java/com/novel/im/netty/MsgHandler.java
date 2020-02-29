/*
 * 作者：刘时明
 * 时间：2020/1/9-14:12
 * 作用：
 */
package com.novel.im.netty;

import com.novel.common.domain.im.Msg;
import com.novel.common.utils.AESUtil;
import com.novel.im.utils.MsgUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MsgHandler extends ChannelInboundHandlerAdapter
{
    static Map<Integer, UserBean> userBeanMap = new ConcurrentHashMap<>();
    static Map<String, Integer> idMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (msg instanceof WebSocketFrame)
        {
            msgHandler(ctx, (WebSocketFrame) msg);
        }
    }

    private void msgHandler(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception
    {
        String message = frame.content().toString(CharsetUtil.UTF_8);
        log.info("接受的消息={}", message);
        Msg msg = MsgUtil.strToMsg(message);
        switch (msg.getCmd())
        {
            case 1:
                handshake(msg.getFormId(), msg.getContent(), ctx);
                break;
            case 2:
                privateSend(msg.getFormId(), msg.getToId(), msg.getContent(), ctx);
                break;
            case 3:
                break;
        }
        ctx.writeAndFlush(new TextWebSocketFrame(message));
    }

    // 客户端握手
    private void handshake(Integer formId, String token, ChannelHandlerContext ctx)
    {
        Msg msg = new Msg();
        int now = (int) (System.currentTimeMillis() / 1000);
        msg.setCreateTime(now);
        if (token.equals("abc"))
        {
            msg.setCmd(1);
            msg.setContent("asdas");
            UserBean bean = new UserBean();
            bean.setAesKey("asdas");
            bean.setChannel(ctx.channel());
            userBeanMap.put(formId, bean);
            idMap.put(ctx.channel().id().toString(), formId);
            log.info("一个用户加入，当前={}", userBeanMap.size());
        } else
        {
            msg.setCmd(0);
            msg.setContent("token错误");
            log.error("一个用户加入失败，token={}", token);
        }
        msg.setLen(msg.getContent().length());
        ctx.writeAndFlush(MsgUtil.msgToStr(msg));
    }

    // 私聊
    private void privateSend(Integer formId, Integer toId, String content, ChannelHandlerContext ctx)
    {
        Msg msg = new Msg();
        int now = (int) (System.currentTimeMillis() / 1000);
        if (userBeanMap.containsKey(formId) && userBeanMap.containsKey(toId))
        {
            msg.setCmd(5);
            msg.setFormId(formId);
            msg.setToId(toId);
            msg.setCreateTime(now);
            UserBean formUserBean = userBeanMap.get(formId);
            UserBean toUserBean = userBeanMap.get(toId);
            // 消息解密
            String temp = AESUtil.aesPKCS7PaddingDecrypt(content, formUserBean.getAesKey());
            msg.setContent(AESUtil.aesPKCS7PaddingEncrypt(temp, toUserBean.getAesKey()));
            toUserBean.getChannel().writeAndFlush(MsgUtil.msgToStr(msg));
        } else
        {
            msg.setCmd(0);
            msg.setContent("私聊消息发送失败");
            log.error("私聊消息发送失败，用户不存在");
            ctx.writeAndFlush(MsgUtil.msgToStr(msg));
        }
    }

    // 用户离开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        String id = ctx.channel().id().toString();
        if (idMap.containsKey(id))
        {
            userBeanMap.remove(idMap.get(id));
            idMap.remove(id);
            log.info("一个用户离开，当前={}，id={}", userBeanMap.size(), id);
        }
    }
}
