/*
 * 作者：刘时明
 * 时间：2020/1/9-10:45
 * 作用：
 */
package com.novel.user.service.dubbo;

import com.novel.common.domain.user.User;
import com.novel.common.domain.user.UserDetails;
import com.novel.user.service.RPCUserService;
import com.novel.user.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0", timeout = 5000)
public class RPCUserServiceImpl implements RPCUserService
{
    @Autowired
    private UserService userService;

    @Override
    public String hello(String name)
    {
        return userService.hello(name);
    }

    @Override
    public String login(String username, String password)
    {
        return null;
    }

    @Override
    public String flushToken(String token)
    {
        return null;
    }

    @Override
    public int register(User user, UserDetails details)
    {
        return 0;
    }
}
