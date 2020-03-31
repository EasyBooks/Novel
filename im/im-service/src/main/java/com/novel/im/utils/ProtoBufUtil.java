package com.novel.im.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.novel.im.proto.DataProto;

/**
 * @program: chat
 * @description:
 * @author: lsm
 * @create: 2020-03-20 18:57
 **/
public class ProtoBufUtil
{
    public static byte[] toReqBytes(DataProto.MsgReq req)
    {
        return req.toByteArray();
    }

    public static byte[] toRspBytes(DataProto.MsgRsp rsp)
    {
        return rsp.toByteArray();
    }

    public static DataProto.MsgReq parseReqBytes(byte[] bytes)
    {
        if (bytes != null)
        {
            try
            {
                return DataProto.MsgReq.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e)
            {

            }
        }
        return null;
    }

    public static DataProto.MsgRsp parseRspBytes(byte[] bytes)
    {
        if (bytes != null)
        {
            try
            {
                return DataProto.MsgRsp.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e)
            {

            }
        }
        return null;
    }
}
