/*
 * 作者：刘时明
 * 时间：2020/1/20-11:51
 * 作用：
 */
package com.novel.im.service;

import com.novel.common.bean.PageList;
import com.novel.common.domain.im.Msg;
import com.novel.common.dto.im.MsgDto;

import java.util.List;

public interface RPCMsgService
{
    /**
     * 消息列表
     *
     * @return
     */
    PageList<MsgDto> msgList(Integer uid,int page,int size);

    /**
     * 读取或者删除消息
     *
     * @param msgIds
     * @return
     */
    boolean readMsg(Long[] msgIds, Integer type);

    /**
     * 系统发送消息
     *
     * @param msg
     * @return
     */
    boolean systemSendMsg(Msg msg);

    /**
     * 在线列表
     *
     * @return
     */
    List<Integer> onLineList();
}
