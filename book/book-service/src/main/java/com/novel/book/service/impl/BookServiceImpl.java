/*
 * 作者：刘时明
 * 时间：2020/1/19-19:38
 * 作用：
 */
package com.novel.book.service.impl;

import com.novel.book.mapper.BookMapper;
import com.novel.book.service.BookService;
import com.novel.book.utils.ConditionUtil;
import com.novel.common.domain.book.Book;
import com.novel.common.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private Snowflake snowflake;

    @Override
    public List<Book> bookList(Map<String, Object> conditionMap)
    {
        return bookMapper.selectByMap(ConditionUtil.getBookCondition(conditionMap));
    }

    @Transactional
    @Override
    public boolean updateBook(Book book)
    {
        int now = (int) (System.currentTimeMillis() / 1000);
        book.setUpdateTime(now);
        if (book.getId() == null)
        {
            book.setId(snowflake.nextId());
            book.setCreateTime(now);
            return bookMapper.insert(book) == 1;
        } else
        {
            return bookMapper.updateById(book) == 1;
        }
    }

    @Override
    public boolean deleteBook(long id)
    {
        return bookMapper.deleteById(id) == 1;
    }

    @Override
    public Book findBook(long id)
    {
        return bookMapper.selectById(id);
    }
}
