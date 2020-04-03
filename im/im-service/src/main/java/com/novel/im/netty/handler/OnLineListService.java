/*
 * 作者：刘时明
 * 时间：2020/3/21-22:01
 * 作用：
 */
package com.novel.im.netty.handler;

import com.novel.common.domain.user.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线列表业务类
 */
@Component
public class OnLineListService
{
    private static final ConcurrentHashMap<String, Long> onLineMap = new ConcurrentHashMap<>(128);
    private static final ConcurrentHashMap<Long, UserBean> userMap = new ConcurrentHashMap<>(128);

    public void onLine(ChannelHandlerContext ctx, Long id)
    {
        System.out.println("一名用户加入,id=" + id);
        onLineMap.put(ctx.channel().id().asShortText(), id);
    }

    public void leave(String ctxId)
    {
        Long id = onLineMap.get(ctxId);
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

    public UserBean getUser(Long id)
    {
        return userMap.get(id);
    }

    @Data
    public static class UserBean
    {
        private User user;
        private Channel channel;

        public UserBean(User user, Channel channel)
        {
            this.user = user;
            this.channel = channel;
        }
    }
}
