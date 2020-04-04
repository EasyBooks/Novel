/*
 * 作者：刘时明
 * 时间：2020/3/20-21:12
 * 作用：
 */
package com.novel.im.netty;

import com.novel.im.netty.enums.ServerType;
import com.novel.im.netty.handler.bytes.BytesHandler;
import com.novel.im.netty.handler.ws.WebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter
public class ServerInit extends ChannelInitializer<SocketChannel>
{
    private String path;
    private ServerType type;

    @Autowired
    private WebSocketHandler webSocketHandler;
    @Autowired
    private BytesHandler bytesHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        ChannelPipeline pipeline = socketChannel.pipeline();
        if (this.type == ServerType.WEB_SOCKET)
        {
            // WS协议基于HTTP，需要HTTP解码器
            pipeline.addLast("http-codec", new HttpServerCodec());
            // 对写大数据流的支持
            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
            // 对HttpMessage聚合，聚合为FullHttpReq和FullHttpRsp
            pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 64));
            // ws服务器处理的协议，指定路由，处理ws握手，ping+pong=心跳
            // ws协议数据以frames传输，不同的数据类型对应不同的frames
            pipeline.addLast(new WebSocketServerProtocolHandler(this.path));
            // 自定义handler
            pipeline.addLast(webSocketHandler);
        } else if(this.type == ServerType.BYTES)
        {
            pipeline.addLast(bytesHandler);
        }
    }
}
