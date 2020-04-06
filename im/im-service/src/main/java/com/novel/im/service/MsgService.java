/*
 * 作者：刘时明
 * 时间：2020/4/2-0:05
 * 作用：
 */
package com.novel.im.service;

import com.novel.common.domain.im.Message;

public interface MsgService extends RPCMsgService
{
    int saveMsg(Message msg);
}
