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
        try
        {
            Optional<byte[]> optionalBytes = BitObjectUtil.objectToBytes(data);
            if (optionalBytes.isPresent())
            {
                this.nsqProducer.produce(topic, optionalBytes.get());
                System.out.println("发送一个数据，topic=" + topic + "，data=" + data);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
