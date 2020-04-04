/*
 * 作者：刘时明
 * 时间：2020/1/19-19:34
 * 作用：
 */
package com.novel.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Chapter;
import com.novel.common.dto.book.ChapterDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChapterMapper extends BaseMapper<Chapter>
{
    @Select("SELECT id,name as chapterName,create_time as createTime FROM t_chapter WHERE book_id=#{bookId}")
    List<ChapterDto> findChapterList(Long bookId);

    @Select("SELECT id,name,create_time as createTime FROM t_chapter WHERE book_id=#{bookId}\n" +
            "ORDER BY sorted DESC limit 1")
    Chapter findLastChapter(Long bookId);

    @Select("SELECT chapter.id,name as chapterName,sorted,content,\n" +
            "(SELECT max(sorted) FROM t_chapter WHERE book_id=book.id) as isLast \n" +
            "FROM t_chapter chapter\n" +
            "LEFT JOIN t_book book ON book.id=chapter.book_id\n" +
            " WHERE chapter.id=#{id}")
    ChapterDto findRead(Long id);

    @Select("SELECT id FROM t_chapter WHERE book_id=#{bookId} AND sorted=1")
    Long findFirstChapter(Long bookId);
}
