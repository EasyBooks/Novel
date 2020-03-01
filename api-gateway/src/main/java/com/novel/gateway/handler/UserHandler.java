/**
 * 作者：刘时明
 * 时间：2019/11/2-16:45
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.common.domain.user.User;
import com.novel.common.domain.user.UserDetails;
import com.novel.common.dto.user.UserDto;
import com.novel.common.utils.JWTUtil;
import com.novel.common.utils.MetadataUtil;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.aspect.annotation.LogInterceptJoinPoint;
import com.novel.user.service.RPCUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserHandler
{
    @Reference(version = "1.0.0",check = false)
    private RPCUserService userService;

    @GetMapping("test")
    public Object test(String name, HttpServletRequest request)
    {
        System.out.println("versions="+ MetadataUtil.getVersions(request));
        return ResultUtil.success(userService.hello(name));
    }

    @LogInterceptJoinPoint
    @PostMapping("login")
    public Object login(String name,String pass)
    {
        User user= userService.login(name, pass);
        if(user==null)
        {
            return ResultUtil.error("登录失败");
        }else
        {
            System.out.println("user="+user);
            UserDto userDto=new UserDto();
            BeanUtils.copyProperties(user,userDto);
            return ResultUtil.success(Map.of("token",JWTUtil.createJWT(user.getUid(),user.getType()),"data",userDto));
        }
    }

    @LogInterceptJoinPoint
    @PostMapping("register")
    public Object register(User user, UserDetails userDetails)
    {
        int result= userService.register(user,userDetails);
        if(result==1)
        {
            return ResultUtil.success("注册成功");
        }else
        {
            return ResultUtil.error("注册失败");
        }
    }
}
