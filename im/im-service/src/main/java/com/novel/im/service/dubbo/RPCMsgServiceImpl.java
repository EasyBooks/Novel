/*
 * 作者：刘时明
 * 时间：2020/4/2-0:11
 * 作用：
 */
package com.novel.im.service.dubbo;

import com.novel.common.bean.PageList;
import com.novel.common.domain.im.Message;
import com.novel.common.dto.im.MsgDto;
import com.novel.im.service.MsgService;
import com.novel.im.service.RPCMsgService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "99.0.0", timeout = 5000)
public class RPCMsgServiceImpl implements RPCMsgService
{
    @Autowired
    private MsgService msgService;

    @Override
    public PageList<MsgDto> msgList(Integer uid, int page, int size)
    {
        return msgService.msgList(uid, page, size);
    }

    @Override
    public boolean readMsg(Long[] msgIds, Integer type)
    {
        return msgService.readMsg(msgIds, type);
    }

    @Override
    public boolean systemSendMsg(Message msg)
    {
        return msgService.systemSendMsg(msg);
    }

    @Override
    public List<Integer> onLineList()
    {
        return msgService.onLineList();
    }
}
