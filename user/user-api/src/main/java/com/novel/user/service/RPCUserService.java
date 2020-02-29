/*
 * 作者：刘时明
 * 时间：2020/1/9-0:51
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.domain.user.User;
import com.novel.common.domain.user.UserDetails;

public interface RPCUserService
{
    String hello(String name);

    String login(String username,String password);

    String flushToken(String token);

    int register(User user, UserDetails details);
}
