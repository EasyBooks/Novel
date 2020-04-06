/**
 * 作者：刘时明
 * 时间：2019/11/2-16:45
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.common.domain.user.UserInfo;
import com.novel.common.domain.user.UserDetails;
import com.novel.common.dto.user.UserDto;
import com.novel.common.utils.JWTUtil;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.aspect.annotation.IdParam;
import com.novel.gateway.aspect.annotation.LogInterceptJoinPoint;
import com.novel.user.service.RPCUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserHandler
{
    @Reference(version = "2.0.0", check = false)
    private RPCUserService userService;

    @LogInterceptJoinPoint
    @PostMapping("login")
    public Object login(String name, String pass)
    {
        UserInfo user = userService.login(name, pass);
        if (user == null)
        {
            return ResultUtil.error("登录失败");
        } else
        {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            UserDetails details = userService.findDetails(user.getUid());
            BeanUtils.copyProperties(details, userDto);
            return ResultUtil.success(Map.of("token", JWTUtil.createJWT(user.getUid(), user.getType()), "data", userDto));
        }
    }

    @LogInterceptJoinPoint
    @PostMapping("register")
    public Object register(UserInfo user)
    {
        int result = userService.register(user);
        if (result == 1)
        {
            return ResultUtil.success("注册成功");
        } else
        {
            return ResultUtil.error("注册失败");
        }
    }

    @PostMapping("flushToken")
    public Object flushToken(@IdParam Integer uid, String token)
    {
        token = userService.flushToken(token, uid);
        if (token == null)
        {
            return ResultUtil.error("error");
        } else
        {
            return ResultUtil.success(token);
        }
    }

    @GetMapping("userInfo/{uid}")
    public Object userInfo(@PathVariable Integer uid)
    {
        UserInfo user = userService.find(uid);
        UserDetails details = userService.findDetails(uid);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        BeanUtils.copyProperties(details, userDto);
        return ResultUtil.success(userDto);
    }
}
