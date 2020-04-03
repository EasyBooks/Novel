package com.novel.im.client;

import org.java_websocket.handshake.ServerHandshake;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-03 16:50
 **/
public interface WebSocketHandler
{
    void onOpen(ServerHandshake serverHandshake);

    void onMessage(String s);

    void onClose(int i, String s, boolean b);

    void onError(Exception e);
}
