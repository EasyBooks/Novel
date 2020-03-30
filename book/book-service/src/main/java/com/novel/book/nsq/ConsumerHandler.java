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
            bookQueryWrapper.eq("third_Id", book.getThirdId())
                    .and(w -> w.eq("platform_Id", book.getPlatformId()));
            Book temp = bookMapper.selectOne(bookQueryWrapper);
            if (temp == null)
            {
                log.info("保存一本小说，{}", book.getTitle());
                bookMapper.insert(book);
            } else
            {
                /**
                 * 此时章节绑定的ID已经重新生成了，需要同步修改
                 */
                long newId = book.getId();
                long oldId = temp.getId();
                bookMapper.updateById(book);
                bookMapper.updateId(oldId, newId);
                log.info("更新一本小说，{}", book.getTitle());
            }
        } else if (data instanceof Chapter)
        {
            Chapter chapter = (Chapter) data;
            QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
            chapterQueryWrapper.eq("third_Id", chapter.getThirdId())
                    .and(w -> w.eq("platform_Id", chapter.getPlatformId()));
            Chapter temp = chapterMapper.selectOne(chapterQueryWrapper);
            if (temp == null)
            {
                chapterMapper.insert(chapter);
                log.info("保存一个章节，{}", chapter.getName());
            } else
            {
                chapter.setId(temp.getId());
                chapterMapper.updateById(chapter);
                log.info("更新一个章节,name={}", chapter.getName());
            }
        }
    }
}
