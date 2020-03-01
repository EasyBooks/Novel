/*
 * 作者：刘时明
 * 时间：2020/1/9-10:45
 * 作用：
 */
package com.novel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.common.domain.user.User;
import com.novel.common.domain.user.UserDetails;
import com.novel.common.utils.AuthUtil;
import com.novel.common.utils.JWTUtil;
import com.novel.common.utils.MD5Util;
import com.novel.common.utils.Snowflake;
import com.novel.user.mapper.UserDetailsMapper;
import com.novel.user.mapper.UserMapper;
import com.novel.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import static com.novel.common.utils.AuthVerifyType.VERIFY_OK;
import static com.novel.common.utils.MD5Util.salt;

@Service
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
    public User login(String username, String password)
    {
        String salt = userMapper.selectSalt(username);
        if (salt != null)
        {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            return userMapper.selectOne(wrapper.eq("username", username).eq("password", MD5Util.password(password, salt)));
        }
        return null;
    }

    @Override
    public String flushToken(String token, Integer uid,Integer userType)
    {
        if (AuthUtil.VerifyToken(token, String.valueOf(uid)) == VERIFY_OK)
        {
            return JWTUtil.createJWT(uid,userType);
        }
        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    @Override
    public int register(User user, UserDetails details)
    {
        int now= (int) (System.currentTimeMillis()/1000);
        user.setId(snowflake.nextId());
        int uid=1000+userMapper.selectCount(null);
        user.setUid(uid);
        user.setType(1);
        user.setStatus(1);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        String salt=salt();
        user.setSalt(salt);
        user.setPassword(MD5Util.password(user.getPassword(), salt));
        details.setUid(uid);
        details.setStatus(1);
        details.setCreateTime(now);
        details.setUpdateTime(now);
        details.setLoginType(1);
        details.setHeadImg("http://img3.doubanio.com/view/photo/l/public/p2507996832.jpg");
        if(userMapper.insert(user)==1&&userDetailsMapper.insert(details)==1)
        {
            return 1;
        }else
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }
}
