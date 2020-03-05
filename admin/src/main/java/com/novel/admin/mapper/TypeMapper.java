/*
 * 作者：刘时明
 * 时间：2020/3/3-22:42
 * 作用：
 */
package com.novel.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Type;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface TypeMapper extends BaseMapper<Type>
{
    @Update("update t_type set status=#{status} where id=#{id}")
    int enable(@Param("status") Integer status, @Param("id") Long id);
}
