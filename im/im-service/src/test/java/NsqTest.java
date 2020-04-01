/*
 * 作者：刘时明
 * 时间：2020/4/1-23:45
 * 作用：
 */

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;
import com.novel.common.domain.book.Book;
import com.novel.common.utils.BitObjectUtil;
import com.novel.im.ImApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class NsqTest
{
    private String nsqHost = "47.95.239.29";
    private int nsqLookupPort = 4161;

    private String topic = "chat";

    private String channel1 = "a";
    private String channel2 = "b";

    public void consumer1()
    {
        NSQLookup lookup = new DefaultNSQLookup();
        Executor executor = Executors.newFixedThreadPool(50);
        lookup.addLookupAddress(nsqHost, nsqLookupPort);
        NSQConsumer registerConsumer = new NSQConsumer(lookup, topic, channel1,
                (message) ->
                {
                    System.out.println("机器a="+new String(message.getMessage()));
                    message.finished();
                });
        registerConsumer.setExecutor(executor);
        registerConsumer.start();
    }

    public void consumer2()
    {
        NSQLookup lookup = new DefaultNSQLookup();
        Executor executor = Executors.newFixedThreadPool(50);
        lookup.addLookupAddress(nsqHost, nsqLookupPort);
        NSQConsumer registerConsumer = new NSQConsumer(lookup, topic, channel2,
                (message) ->
                {
                    System.out.println("机器b="+new String(message.getMessage()));
                    message.finished();
                });
        registerConsumer.setExecutor(executor);
        registerConsumer.start();
    }

    /**
     * NSQ的消息会分发给topic下的所有channel（相同channel除外）
     * @throws NSQException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    @Test
    public void producer() throws NSQException, TimeoutException, InterruptedException
    {
        new Thread(this::consumer1).start();
        new Thread(this::consumer2).start();
        NSQProducer nsqProducer = new NSQProducer();
        nsqProducer.addAddress(nsqHost, 4150).start();
        for (int i = 0; i < 1; i++)
        {
            nsqProducer.produce(topic, "hello".getBytes());
        }
        Thread.sleep(10 * 1000);
    }
}
