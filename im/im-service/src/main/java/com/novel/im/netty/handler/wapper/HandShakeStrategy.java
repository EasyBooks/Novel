/*
 * 作者：刘时明
 * 时间：2020/3/21-14:23
 * 作用：
 */
package com.novel.im.netty.handler.wapper;

import com.google.gson.Gson;
import com.novel.im.proto.DataProto;
import com.novel.im.utils.DESUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HandShakeStrategy implements HandlerStrategy<DataProto.HandShakeReq>
{
    private static final Gson gson=new Gson();
    private static final DataProto.MsgType TYPE = DataProto.MsgType.HANDSHAKE_MSG;

    @Override
    public DataProto.MsgRsp protoBufHandler(DataProto.HandShakeReq req) {
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

    @Override
    public String jsonHandler(String json)
    {
        final HandShakeType handShakeType = gson.fromJson(json, HandShakeType.class);
        // 校验token
        return UUID.randomUUID().toString().substring(0,5);
    }

    @Data
    static class HandShakeType
    {
        private String token;
    }
}
