/*
 * 作者：刘时明
 * 时间：2020/3/5-11:01
 * 作用：
 */
package com.novel.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.admin.security.entity.Admin;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper extends BaseMapper<Admin>
{
    @Select("select * from t_admin where username=#{username}")
    Admin findByUsername(String username);
}
