/*
 * 作者：刘时明
 * 时间：2020/3/21-14:23
 * 作用：
 */
package com.novel.im.netty.wapper;

import com.novel.common.domain.im.Message;
import com.novel.common.utils.AuthUtil;
import com.novel.common.utils.JWTUtil;
import com.novel.im.proto.DataProto;
import com.novel.im.service.UserService;
import com.novel.im.utils.DESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HandShakeStrategy implements HandlerStrategy<DataProto.HandShakeReq>
{
    private static final DataProto.MsgType TYPE = DataProto.MsgType.HANDSHAKE_MSG;

    @Autowired
    private UserService userService;

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
    public String jsonHandler(Message msg)
    {
        // 校验token
        try {
            int uid = Integer.parseInt(AuthUtil.parseToken(msg.getContent()));
            if(uid==-1)
            {
                return null;
            }
            String publicKey= UUID.randomUUID().toString().substring(0,5);
            if(userService.setPublicKey(uid,publicKey)==1)
            {
                return String.format("%d$%s",uid,publicKey);
            }
            return null;
        }catch (Exception e)
        {
            return null;
        }
    }
}
