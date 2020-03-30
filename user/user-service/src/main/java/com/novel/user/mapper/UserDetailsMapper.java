/*
 * 作者：刘时明
 * 时间：2020/3/1-8:36
 * 作用：
 */
package com.novel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.user.User;
import com.novel.common.domain.user.UserDetails;
import com.novel.common.dto.user.AuthorDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDetailsMapper extends BaseMapper<UserDetails>
{
}
