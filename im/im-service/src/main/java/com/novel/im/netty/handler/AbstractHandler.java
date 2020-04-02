package com.novel.im.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 11:18
 **/
@Slf4j
public abstract class AbstractHandler extends ChannelInboundHandlerAdapter
{
    // 用户加入
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        log.info("一个用户加入，当前={}", onLineListService().size());
    }

    // 用户离开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        String id=ctx.channel().id().toString();
        onLineListService().leave(id);
        log.info("一个用户离开，当前={}，id={}", onLineListService().size(), id);
    }

    // 连接异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        log.info("一个用户连接异常，cause={}", cause.getMessage());
    }

    public abstract OnLineListService onLineListService();
}
