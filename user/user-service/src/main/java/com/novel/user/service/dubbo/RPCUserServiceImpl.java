/*
 * 作者：刘时明
 * 时间：2020/1/9-10:45
 * 作用：
 */
package com.novel.user.service.dubbo;

import com.novel.common.domain.user.UserInfo;
import com.novel.common.domain.user.UserDetails;
import com.novel.common.dto.user.AuthorDto;
import com.novel.common.dto.user.CircleDto;
import com.novel.user.service.RPCUserService;
import com.novel.user.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(version = "99.0.0", timeout = 5000)
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
    public UserInfo login(String username, String password)
    {
        return userService.login(username, password);
    }

    @Override
    public String flushToken(String token,Integer uid)
    {
        return userService.flushToken(token, uid);
    }

    @Override
    public int register(UserInfo user)
    {
        return userService.register(user);
    }

    @Override
    public UserInfo find(Integer uid)
    {
        return userService.find(uid);
    }

    @Override
    public UserInfo find(Long id)
    {
        return userService.find(id);
    }

    @Override
    public UserDetails findDetails(Integer uid)
    {
        return userService.findDetails(uid);
    }

    @Override
    public List<AuthorDto> findAuthors(List<Long> bookIds)
    {
        return userService.findAuthors(bookIds);
    }

    @Override
    public int saveAuthor(Long userId, Long bookId)
    {
        return userService.saveAuthor(userId, bookId);
    }
}
