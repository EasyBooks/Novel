/*
 * 作者：刘时明
 * 时间：2020/4/2-0:15
 * 作用：
 */
package com.novel.im.nsq;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;
import com.novel.common.domain.book.Book;
import com.novel.common.domain.im.Msg;
import com.novel.common.utils.BitObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class MsgConsumer
{
    @Value("${nsq.lookup.host}")
    private String nsqHost;

    @Value("${nsq.lookup.port}")
    private Integer nsqLookupPort;

    @Value("${nsq.topic}")
    private String bookTopic;

    @Value("${nsq.channel}")
    private String channel;

    @PostConstruct
    public void initConsumer()
    {
        NSQLookup lookup = new DefaultNSQLookup();
        Executor executor = Executors.newFixedThreadPool(50);
        lookup.addLookupAddress(nsqHost, nsqLookupPort);
        msgConsumer(lookup, executor);
    }

    @Async
    public void msgConsumer(NSQLookup lookup, Executor executor)
    {
        NSQConsumer registerConsumer = new NSQConsumer(lookup, bookTopic, channel,
                (message) ->
                {
                    Optional<Msg> optional = BitObjectUtil.bytesToObject(message.getMessage());
                    optional.ifPresent(o ->
                    {
                        System.out.println("收到消息");
                    });
                    message.finished();
                });
        registerConsumer.setExecutor(executor);
        registerConsumer.start();
    }
}
