/*
 * 作者：刘时明
 * 时间：2020/3/21-14:21
 * 作用：
 */
package com.novel.im.netty.handler.wapper;

import com.novel.im.proto.DataProto;
import org.springframework.stereotype.Component;

@Component
public class AckStrategy implements HandlerStrategy<DataProto.MsgReq>
{
    @Override
    public DataProto.MsgRsp protoBufHandler(DataProto.MsgReq req) {
        return null;
    }

    @Override
    public String jsonHandler(String json) {
        return null;
    }
}
