/*
 * 作者：刘时明
 * 时间：2020/3/21-13:57
 * 作用：
 */
package com.novel.im.netty.handler.wapper;

import com.novel.im.proto.DataProto;

/**
 * 消息处理策略接口
 */
public interface HandlerStrategy<E>
{
    /**
     * protoBuf处理
     * @param req
     * @return
     */
    DataProto.MsgRsp protoBufHandler(E req);

    /**
     * json处理
     * @param json
     * @return
     */
    String jsonHandler(String json);
}
