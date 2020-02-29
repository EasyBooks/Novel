/*
 * 作者：刘时明
 * 时间：2020/1/9-10:18
 * 作用：
 */
package com.novel.book.nsq;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.nsq.NsqBookDto;
import com.novel.common.utils.BitObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class NsqConsumer implements ApplicationRunner
{
    @Value("${nsq.lookup.host}")
    private String nsqHost;

    @Value("${nsq.lookup.port}")
    private Integer nsqLookupPort;

    @Value("${nsq.topic}")
    private String nsqTopic;

    @Value("${nsq.channel}")
    private String channel;

    @Autowired
    private ConsumerHandler consumerHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        NSQLookup lookup = new DefaultNSQLookup();
        Executor executor = Executors.newFixedThreadPool(20);
        lookup.addLookupAddress(nsqHost, nsqLookupPort);
        NSQConsumer registerConsumer = new NSQConsumer(lookup, nsqTopic, channel,
                (message) ->
                {
                    Optional<NsqBookDto> optional = BitObjectUtil.bytesToObject(message.getMessage());
                    optional.ifPresent(nsqBookDto -> consumerHandler.bookHandler(nsqBookDto));
                    message.finished();
                });
        registerConsumer.setExecutor(executor);
        registerConsumer.start();
    }


}
