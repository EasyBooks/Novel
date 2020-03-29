/*
 * 作者：刘时明
 * 时间：2020/2/28-12:45
 * 作用：
 */
package com.novel.book.nsq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.book.mapper.BookMapper;
import com.novel.book.mapper.ChapterMapper;
import com.novel.common.domain.book.Book;
import com.novel.common.domain.book.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerHandler
{
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ChapterMapper chapterMapper;

    public void bookHandler(Object data)
    {
        if (data instanceof Book)
        {
            Book book = (Book) data;
            if (book.getTitle() == null) return;
            QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
            bookQueryWrapper.eq("third_Id", book.getThirdId());
            Book temp = bookMapper.selectOne(bookQueryWrapper);
            if (temp == null)
            {
                bookMapper.insert(book);
            } else
            {
                bookMapper.updateById(book);
            }
            log.info("保存一本小说，{}", book.getTitle());
        } else if (data instanceof Chapter)
        {
            Chapter chapter = (Chapter) data;
            QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
            chapterQueryWrapper.eq("third_Id", chapter.getThirdId());
            Chapter temp = chapterMapper.selectOne(chapterQueryWrapper);
            if (temp == null)
            {
                chapterMapper.insert(chapter);
            } else
            {
                chapterMapper.updateById(chapter);
            }
            log.info("保存一个章节，{}", chapter.getName());
        }
    }
}
