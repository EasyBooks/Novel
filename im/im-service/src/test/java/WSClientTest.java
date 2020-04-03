import com.novel.im.client.WSClient;
import com.novel.im.client.WebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-03 16:44
 **/
@Slf4j
public class WSClientTest {
    @Test
    public void test()
    {
        WSClient client = WSClient.create("ws://47.103.211.234:8888/im", new WebSocketHandler()
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
        client.send("hello");
        client.close();
    }
}
