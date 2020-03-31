/*
 * 作者：刘时明
 * 时间：2020/3/20-21:09
 * 作用：
 */
package com.novel.im.server;

import com.novel.common.define.Task;
import com.novel.im.config.ExecutorConfig;
import com.novel.im.enums.ServerType;
import com.novel.im.handler.ServerInit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettyServer implements ApplicationRunner, Task
{
    @Value("${im.port}")
    private int port;
    @Value("${im.path}")
    private String path;

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;

    public NettyServer()
    {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInit(path, ServerType.BYTES))
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void startServer()
    {
        try
        {
            ChannelFuture future = server.bind(port).sync();
            log.info("服务器初始化完成...");
            future.channel().closeFuture().sync();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }

    @Override
    public void taskWork(Object args)
    {
        this.startServer();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        // run方法运行在main线程，而startServer会阻塞
        ExecutorConfig.executor(this, null);
    }
}
