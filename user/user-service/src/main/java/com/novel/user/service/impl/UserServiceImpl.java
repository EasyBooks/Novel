/*
 * 作者：刘时明
 * 时间：2020/1/9-10:45
 * 作用：
 */
package com.novel.user.service.impl;

import com.novel.common.domain.user.User;
import com.novel.common.domain.user.UserDetails;
import com.novel.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Override
    public String hello(String name)
    {
        return String.format("你好啊，%s", name);
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
