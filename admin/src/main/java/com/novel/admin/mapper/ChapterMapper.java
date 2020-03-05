/*
 * 作者：刘时明
 * 时间：2020/3/3-22:41
 * 作用：
 */
package com.novel.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Chapter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ChapterMapper extends BaseMapper<Chapter>
{
    @Update("update t_chapter set status=#{status} where book_id=#{bookId}")
    int enableByBookId(@Param("status") Integer status, @Param("bookId") Long bookId);

    @Update("update t_chapter set status=#{status} where id=#{id}")
    int enable(@Param("status") Integer status, @Param("id") Long id);
}