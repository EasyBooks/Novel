import com.novel.im.client.WSClient;
import com.novel.im.client.WebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-03 16:44
 **/
@Slf4j
public class WSClientTest
{
    private String ws_url = "ws://47.103.211.234:8888/im";
    private String ws_dev_url = "ws://127.0.0.1:8888/im";

    @Test
    public void test() throws Exception
    {
        WSClient client=WSClient.create(ws_dev_url, new WebSocketHandler()
        {
            @Override
            public void onOpen(ServerHandshake serverHandshake)
            {

            }

            @Override
            public void onMessage(String s)
            {
                System.out.println("收到消息="+s);
            }

            @Override
            public void onClose(int i, String s, boolean b)
            {

            }

            @Override
            public void onError(Exception e)
            {

            }
        });
        while (!client.getReadyState().equals(ReadyState.OPEN))
        {
            log.info("正在连接...");
        }
        //连接成功,发送信息
        client.send("哈喽,连接一下啊");
    }

    private void createConn() throws Exception
    {
        WSClient client = WSClient.create(ws_dev_url, new WebSocketHandler()
        {
            @Override
            public void onOpen(ServerHandshake serverHandshake)
            {
                log.info("连接成功");
            }

            @Override
            public void onMessage(String s)
            {
                log.info("收到消息");
            }

            @Override
            public void onClose(int i, String s, boolean b)
            {
                log.info("退出连接");
            }

            @Override
            public void onError(Exception e)
            {

            }
        });
        while (!client.getReadyState().equals(ReadyState.OPEN))
        {
            System.out.println("连接中···请稍后");
        }
        for (int i = 0; i < 1000; i++)
        {
            client.send("hello");
            Thread.sleep(1000);
        }
        client.close();
    }
}
