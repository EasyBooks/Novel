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
import com.novel.common.dto.nsq.NsqBookDto;
import com.novel.common.utils.BitObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConsumerHandler
{
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ChapterMapper chapterMapper;

    @Async
    public void bookHandler(NsqBookDto nsqBookDto)
    {
        switch (nsqBookDto.getCode())
        {
            // 书籍对象
            case 1:
                Optional<Book> bookOptional = BitObjectUtil.bytesToObject(nsqBookDto.getData());
                Book book = bookOptional.get();
                QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
                bookQueryWrapper.eq("id", book.getId());
                if (bookMapper.selectCount(bookQueryWrapper) == 0)
                {
                    bookMapper.insert(book);
                } else
                {
                    bookMapper.updateById(book);
                }
                break;
            // 章节对象
            case 2:
                Optional<Chapter> chapterOptional = BitObjectUtil.bytesToObject(nsqBookDto.getData());
                Chapter chapter=chapterOptional.get();
                QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
                chapterQueryWrapper.eq("id", chapter.getId());
                if (chapterMapper.selectCount(chapterQueryWrapper) == 0)
                {
                    chapterMapper.insert(chapter);
                } else
                {
                    chapterMapper.updateById(chapter);
                }
                break;
        }
    }
}
