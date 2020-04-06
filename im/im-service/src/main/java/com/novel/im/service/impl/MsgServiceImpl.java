/*
 * 作者：刘时明
 * 时间：2020/4/2-0:06
 * 作用：
 */
package com.novel.im.service.impl;

import com.novel.common.bean.PageList;
import com.novel.common.bean.Snowflake;
import com.novel.common.domain.BaseEntity;
import com.novel.common.domain.im.Message;
import com.novel.common.dto.im.MsgDto;
import com.novel.im.mapper.MsgMapper;
import com.novel.im.mapper.MsgReadMapper;
import com.novel.im.nsq.MsgProducer;
import com.novel.im.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService
{
    @Autowired
    private Snowflake snowflake;
    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private MsgReadMapper msgReadMapper;
    @Autowired
    private MsgProducer producer;

    @Override
    public PageList<MsgDto> msgList(Integer uid,int page,int size)
    {
        return null;
    }

    @Override
    public boolean readMsg(Long[] msgIds, Integer type)
    {
        return false;
    }

    @Override
    public boolean systemSendMsg(Message msg)
    {
        producer.produce(msg);
        BaseEntity.initEntity(msg,snowflake);
        return msgMapper.insert(msg)==1;
    }

    @Override
    public List<Integer> onLineList()
    {
        return null;
    }

    @Override
    public int saveMsg(Message msg)
    {
        return msgMapper.insert(msg);
    }
}
