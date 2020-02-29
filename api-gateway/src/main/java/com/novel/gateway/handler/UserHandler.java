/**
 * 作者：刘时明
 * 时间：2019/11/2-16:45
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.common.utils.MetadataUtil;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.aspect.annotation.LogInterceptJoinPoint;
import com.novel.user.service.RPCUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/user")
public class UserHandler
{
    @Reference(version = "1.0.0", check = false)
    private RPCUserService userService;

    @LogInterceptJoinPoint
    @GetMapping("test")
    public Object test(String name, HttpServletRequest request)
    {
        System.out.println("versions="+ MetadataUtil.getVersions(request));
        return ResultUtil.success(userService.hello(name));
    }
}
