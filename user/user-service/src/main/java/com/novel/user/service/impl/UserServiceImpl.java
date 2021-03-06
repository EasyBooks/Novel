/*
 * 作者：刘时明
 * 时间：2020/1/9-10:45
 * 作用：
 */
package com.novel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.common.domain.BaseEntity;
import com.novel.common.domain.user.UserInfo;
import com.novel.common.domain.user.UserDetails;
import com.novel.common.dto.user.AuthorDto;
import com.novel.common.dto.user.CircleDto;
import com.novel.common.utils.AuthUtil;
import com.novel.common.utils.DateUtil;
import com.novel.common.utils.JWTUtil;
import com.novel.common.utils.MD5Util;
import com.novel.common.bean.Snowflake;
import com.novel.user.mapper.UserDetailsMapper;
import com.novel.user.mapper.UserMapper;
import com.novel.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

import static com.novel.common.utils.AuthVerifyType.VERIFY_OK;
import static com.novel.common.utils.MD5Util.salt;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsMapper userDetailsMapper;
    @Autowired
    private Snowflake snowflake;

    @Override
    public String hello(String name)
    {
        return String.format("你好啊，%s", name);
    }

    @Override
    public UserInfo login(String username, String password)
    {
        String salt = userMapper.selectSalt(username);
        if (salt != null)
        {
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            return userMapper.selectOne(wrapper.eq("username", username).eq("password", MD5Util.password(password, salt)));
        }
        return null;
    }

    @Override
    public String flushToken(String token, Integer uid)
    {
        UserInfo user = this.find(uid);
        if (user == null) return null;
        if (AuthUtil.VerifyToken(token, String.valueOf(uid)) == VERIFY_OK)
        {
            return JWTUtil.createJWT(uid, user.getType());
        }
        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int register(UserInfo user)
    {
        int now = DateUtil.nowTime();
        int uid = 1000 + userMapper.selectCount(null);
        user.setUid(uid);
        user.setType(1);
        BaseEntity.initEntity(user,snowflake);
        String salt = salt();
        user.setSalt(salt);
        user.setPassword(MD5Util.password(user.getPassword(), salt));
        UserDetails details=new UserDetails();
        details.setHeadImg("");
        details.setId(user.getId());
        details.setUid(uid);
        details.setStatus(1);
        details.setCreateTime(now);
        details.setUpdateTime(now);
        details.setLoginType(1);
        if (userMapper.insert(user) == 1 && userDetailsMapper.insert(details) == 1)
        {
            return 1;
        } else
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    @Override
    public UserInfo find(Integer uid)
    {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public UserInfo find(Long id)
    {
        return userMapper.selectById(id);
    }

    @Override
    public UserDetails findDetails(Integer uid)
    {
        QueryWrapper<UserDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        return userDetailsMapper.selectOne(wrapper);
    }

    @Override
    public List<AuthorDto> findAuthors(List<Long> bookIds)
    {
        return userMapper.findAuthors(bookIds);
    }

    @Override
    public int saveAuthor(Long userId, Long bookId)
    {
        int now = DateUtil.nowTime();
        UserInfo user = new UserInfo();
        user.setId(snowflake.nextId());
        user.setUpdateTime(now);
        user.setCreateTime(now);
        user.setStatus(1);
        return userMapper.insertAuthor(user,userId, bookId);
    }
}
