/*
 * 作者：刘时明
 * 时间：2020/3/29-13:02
 * 作用：
 */
package com.novel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.user.Collection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectionMapper extends BaseMapper<Collection>
{
    @Select("select book_id from t_collection where status=1 and uid=#{uid} limit #{page},#{size}")
    List<Long> collectionBookIds(@Param("uid") Integer uid,@Param("page") int page, @Param("size")int size);

    @Select("select COUNT(*) from t_collection where status=1 and uid=#{uid}")
    int countCollection(@Param("uid") Integer uid);
}
