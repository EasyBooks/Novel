/*
 * 作者：刘时明
 * 时间：2020/4/4-17:59
 * 作用：
 */
package com.novel.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RecordMapper extends BaseMapper<Record>
{
    @Select("SELECT chapter_id as chapterId FROM t_record WHERE book_id=#{bookId} AND uid=#{uid}")
    Long findChapterRecord(@Param("bookId") long bookId,@Param("uid")  int uid);
}
