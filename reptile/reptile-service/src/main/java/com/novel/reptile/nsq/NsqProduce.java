/*
 * 作者：刘时明
 * 时间：2020/1/9-9:44
 * 作用：
 */
package com.novel.reptile.nsq;

import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;
import com.novel.common.utils.BitObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Component
public class NsqProduce
{
    @Value("${nsq.produce.host}")
    private String nsqHost;

    @Value("${nsq.produce.port}")
    private Integer nsqPort;

    @Value("${nsq.topic}")
    private String nsqTopic;

    private NSQProducer nsqProducer;

    @PostConstruct
    public void produceInit()
    {
        this.nsqProducer = new NSQProducer();
        this.nsqProducer.addAddress(nsqHost, nsqPort).start();
    }

    @Async
    public void produce(Object data)
    {
        try
        {
            Optional<byte[]> optionalBytes = BitObjectUtil.objectToBytes(data);
            if (optionalBytes.isPresent())
            {
                this.nsqProducer.produce(nsqTopic, optionalBytes.get());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Async
    public void produce(String topic,Object data)
    {
        try
        {
            Optional<byte[]> optionalBytes = BitObjectUtil.objectToBytes(data);
            if (optionalBytes.isPresent())
            {
                this.nsqProducer.produce(topic, optionalBytes.get());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void produce(byte[] data) throws NSQException, TimeoutException
    {
        this.nsqProducer.produce(nsqTopic, data);
    }

    public void produce(String msg) throws NSQException, TimeoutException
    {
        this.nsqProducer.produce(nsqTopic, msg.getBytes());
    }
}
