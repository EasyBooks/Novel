/*
 * 作者：刘时明
 * 时间：2020/3/21-14:23
 * 作用：
 */
package com.novel.im.handler.strategy;

import com.novel.im.handler.HandlerStrategy;
import com.novel.im.proto.DataProto;
import com.novel.im.utils.DESUtil;

public class HandShakeStrategy implements HandlerStrategy<DataProto.HandShakeReq>
{
    private static final DataProto.MsgType TYPE = DataProto.MsgType.HANDSHAKE_MSG;

    @Override
    public DataProto.MsgRsp handler(DataProto.HandShakeReq req)
    {
        try
        {
            String desKey = DESUtil.getKey();
            DataProto.HandShakeRsp rsp = DataProto.HandShakeRsp.newBuilder()
                    .setCode(DataProto.ResultCode.OK)
                    .setAesKey(desKey)
                    .build();
            return DataProto.MsgRsp.newBuilder()
                    .setMsgType(TYPE)
                    .setHandShakeRsp(rsp)
                    .build();
        }catch (Exception e)
        {
            return null;
        }
    }
}
