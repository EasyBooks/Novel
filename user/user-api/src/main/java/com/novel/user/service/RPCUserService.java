/*
 * 作者：刘时明
 * 时间：2020/1/9-0:51
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.domain.user.User;
import com.novel.common.domain.user.UserDetails;
import com.novel.common.dto.book.CircleDto;
import com.novel.common.dto.user.AuthorDto;

import java.util.List;

public interface RPCUserService
{
    String hello(String name);

    User login(String username, String password);

    String flushToken(String token, Integer uid);

    int register(User user, UserDetails details);

    User find(Integer uid);

    User find(Long id);

    UserDetails findDetails(Integer uid);

    List<AuthorDto> findAuthors(List<Long> bookIds);

    int saveAuthor(Long userId,Long bookId);

    List<CircleDto> findCircleByBook(Long bookId);
}
