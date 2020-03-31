/*
 * 作者：刘时明
 * 时间：2020/3/21-13:57
 * 作用：
 */
package com.novel.im.handler;

import com.novel.im.proto.DataProto;

/**
 * 消息处理策略接口
 */
public interface HandlerStrategy<E>
{
    DataProto.MsgRsp handler(E req);
}
