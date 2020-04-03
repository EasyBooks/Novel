package com.novel.im.client;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-03 16:50
 **/
public class WSClient extends WebSocketClient
{
    private WebSocketHandler handler;

    public WSClient(URI uri,WebSocketHandler handler)
    {
        super(uri);
        this.handler=handler;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake)
    {
        handler.onOpen(serverHandshake);
    }

    @Override
    public void onMessage(String s)
    {
        handler.onMessage(s);
    }

    @Override
    public void onClose(int i, String s, boolean b)
    {
        handler.onClose(i, s, b);
    }

    @Override
    public void onError(Exception e)
    {
        handler.onError(e);
    }

    public static WSClient create(String url, WebSocketHandler handler)
    {
        return new WSClient(URI.create(url),handler);
    }
}
