/*
 * 作者：刘时明
 * 时间：2020/4/6-10:59
 * 作用：
 */
package com.novel.im.service.impl;

import com.novel.im.mapper.UserMapper;
import com.novel.im.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;

    @Override
    public int setPublicKey(Integer uid, String publicKey)
    {
        return userMapper.updatePublicKey(uid,publicKey);
    }
}
