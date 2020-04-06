/*
 * 作者：刘时明
 * 时间：2020/4/2-0:15
 * 作用：
 */
package com.novel.im.nsq;

import com.github.brainlag.nsq.NSQProducer;
import com.novel.common.domain.im.Message;
import com.novel.common.utils.BitObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@Slf4j
public class MsgProducer
{
    @Value("${nsq.produce.host}")
    private String nsqHost;

    @Value("${nsq.produce.port}")
    private Integer nsqPort;

    @Value("${nsq.topic}")
    private String topic;

    private NSQProducer nsqProducer;

    @PostConstruct
    public void produceInit()
    {
        this.nsqProducer = new NSQProducer();
        this.nsqProducer.addAddress(nsqHost, nsqPort).start();
    }

    @Async
    public void produce(Message data)
    {
        Optional<byte[]> optionalBytes = BitObjectUtil.objectToBytes(data);
        try
        {
            if (optionalBytes.isPresent())
            {
                this.nsqProducer.produce(topic, optionalBytes.get());
            }
        } catch (Exception e)
        {
            // 发送数据失败，加入重试队列
            log.error("produce发送消息失败,err={}", e.getMessage());
            trySend(data, 1);
        }
    }

    private void trySend(Message data, int count)
    {
        try
        {
            Thread.sleep(count * 1000);
            this.produce(data);
        } catch (Exception e)
        {
            log.error("trySend重试失败,err={},当前次数={}", e.getMessage(), count);
            trySend(data, ++count);
        }
    }
}
