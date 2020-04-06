/*
 * 作者：刘时明
 * 时间：2020/4/6-12:51
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.common.define.AuthType;
import com.novel.common.domain.im.Message;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.aspect.annotation.Auth;
import com.novel.im.service.RPCMsgService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/im")
public class ImHandler
{
    @Reference(version = "2.0.0", check = false)
    private RPCMsgService msgService;

    @Auth(value = {AuthType.ADMIN})
    @PostMapping("systemMsg")
    public Object systemMsg(@RequestBody Message msg)
    {
        if(msgService.systemSendMsg(msg))
        {
            return ResultUtil.success("ok");
        }
        return ResultUtil.error("发送失败");
    }

    @Auth(value = {AuthType.ADMIN})
    @GetMapping("test")
    public Object test()
    {
        return "test";
    }

    @Auth(value = {AuthType.USER})
    @GetMapping("test2")
    public Object test2()
    {
        return "test";
    }

    @GetMapping("test3")
    public Object test3()
    {
        return "test";
    }
}
