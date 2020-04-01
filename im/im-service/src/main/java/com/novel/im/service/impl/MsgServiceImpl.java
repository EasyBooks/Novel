/*
 * 作者：刘时明
 * 时间：2020/4/2-0:06
 * 作用：
 */
package com.novel.im.service.impl;

import com.novel.common.bean.PageList;
import com.novel.common.domain.im.Msg;
import com.novel.common.dto.im.MsgDto;
import com.novel.im.mapper.MsgMapper;
import com.novel.im.mapper.MsgReadMapper;
import com.novel.im.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService
{
    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private MsgReadMapper msgReadMapper;

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
    public boolean systemSendMsg(Msg msg)
    {
        return false;
    }

    @Override
    public List<Integer> onLineList()
    {
        return null;
    }
}
