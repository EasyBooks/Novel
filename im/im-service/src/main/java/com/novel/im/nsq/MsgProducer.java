/*
 * 作者：刘时明
 * 时间：2020/4/2-0:15
 * 作用：
 */
package com.novel.im.nsq;

import com.github.brainlag.nsq.NSQProducer;
import com.novel.common.utils.BitObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class MsgProducer
{
    @Value("${nsq.produce.host}")
    private String nsqHost;

    @Value("${nsq.produce.port}")
    private Integer nsqPort;

    private NSQProducer nsqProducer;

    @PostConstruct
    public void produceInit()
    {
        this.nsqProducer = new NSQProducer();
        this.nsqProducer.addAddress(nsqHost, nsqPort).start();
    }

    @Async
    public void produce(String topic, Object data)
    {
        Optional<byte[]> optionalBytes = BitObjectUtil.objectToBytes(data);
        try
        {
            if (optionalBytes.isPresent())
            {
                this.nsqProducer.produce(topic, optionalBytes.get());;
            }
        } catch (Exception e)
        {
            // 发送数据失败，加入重试队列
            trySend(topic, data);
        }
    }

    private void trySend(String topic, Object data)
    {
        try
        {
            Thread.sleep(2 * 1000);
            this.produce(topic, data);
        } catch (Exception e)
        {
            System.err.println("MQ发送重试");
            trySend(topic, data);
        }
    }
}
