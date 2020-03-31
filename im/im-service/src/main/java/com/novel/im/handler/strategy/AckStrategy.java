/*
 * 作者：刘时明
 * 时间：2020/3/21-14:21
 * 作用：
 */
package com.novel.im.handler.strategy;

import com.novel.im.handler.HandlerStrategy;
import com.novel.im.proto.DataProto;

public class AckStrategy implements HandlerStrategy<DataProto.MsgReq>
{
    @Override
    public DataProto.MsgRsp handler(DataProto.MsgReq req)
    {
        return null;
    }
}
