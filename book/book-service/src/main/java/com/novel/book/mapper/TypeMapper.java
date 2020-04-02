/*
 * 作者：刘时明
 * 时间：2020/1/19-19:35
 * 作用：
 */
package com.novel.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Type;
import com.novel.common.dto.book.TypeDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TypeMapper extends BaseMapper<Type>
{
    @Select("SELECT t.id,t.pic,t.name,(SELECT count(*) FROM t_book WHERE type_id=t.id) as bookCount FROM t_type t WHERE `status`=1")
    List<TypeDto> list();
}
