/*
 * 作者：刘时明
 * 时间：2020/3/1-8:12
 * 作用：
 */
package com.novel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.user.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User>
{
    @Select("SELECT salt FROM user WHERE username=#{username} AND status=1")
    String selectSalt(String username);
}
