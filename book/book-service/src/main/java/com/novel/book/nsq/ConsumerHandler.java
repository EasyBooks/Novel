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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
            QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
            bookQueryWrapper.eq("id", book.getId());
            if (bookMapper.selectCount(bookQueryWrapper) == 0)
            {
                bookMapper.insert(book);
            } else
            {
                bookMapper.updateById(book);
            }
            System.out.println("一个book入库");
        } else if (data instanceof Chapter)
        {
            Chapter chapter = (Chapter) data;
            QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
            chapterQueryWrapper.eq("id", chapter.getId());
            if (chapterMapper.selectCount(chapterQueryWrapper) == 0)
            {
                chapterMapper.insert(chapter);
            } else
            {
                chapterMapper.updateById(chapter);
            }
            System.out.println("一个chapter入库");
        }
    }
}
