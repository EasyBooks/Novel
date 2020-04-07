/*
 * 作者：刘时明
 * 时间：2020/3/21-22:01
 * 作用：
 */
package com.novel.im.netty.handler;

import com.novel.common.domain.user.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 在线列表业务类
 */
@Component
public class OnLineListService
{
    // private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<String, Integer> onLineMap = new ConcurrentHashMap<>(128);
    private static final ConcurrentHashMap<Integer, UserBean> userMap = new ConcurrentHashMap<>(128);

    public void onLine(ChannelHandlerContext ctx, int id)
    {
        System.out.println("一名用户加入,id=" + id);
        onLineMap.put(ctx.channel().id().asShortText(), id);
        userMap.put(id, new UserBean(null, ctx.channel()));
    }

    public void leave(String ctxId)
    {
        Integer id = onLineMap.get(ctxId);
        System.out.println("一名用户退出,id=" + id);
        if (id != null)
        {
            onLineMap.remove(ctxId);
            userMap.remove(id);
        }
    }

    public int size()
    {
        return onLineMap.size();
    }

    public UserBean getUser(int id)
    {
        return userMap.get(id);
    }

    public void foreachSend(Consumer<Channel> consumer)
    {
        userMap.forEach((k, v) -> consumer.accept(v.channel));
    }

    @Data
    public static class UserBean
    {
        private UserInfo user;
        private Channel channel;

        public UserBean(UserInfo user, Channel channel)
        {
            this.user = user;
            this.channel = channel;
        }
    }
}
