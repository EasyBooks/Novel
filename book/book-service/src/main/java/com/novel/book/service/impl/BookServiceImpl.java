/*
 * 作者：刘时明
 * 时间：2020/1/19-19:38
 * 作用：
 */
package com.novel.book.service.impl;

import com.novel.book.mapper.BookMapper;
import com.novel.book.service.BookService;
import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDto;
import com.novel.common.bean.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private Snowflake snowflake;

    @Override
    public PageList<BookDto> bookList(Map<String, Object> conditionMap, int page, int size)
    {
        if (conditionMap == null)
        {
            conditionMap = new HashMap<>();
        }
        conditionMap.put("page", page);
        conditionMap.put("size", size);
        List<BookDto> bookList = bookMapper.queryBookDto(conditionMap);
        return PageList.of(bookList, bookMapper.countBookDto(conditionMap));
    }


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

    @Override
    public PageList<BookDto> findCollection(List<Long> idList, int page, int size)
    {
        if(idList.size()==0) return PageList.of(new ArrayList<>(),0);
        Map<String,Object> conditionMap = new HashMap<>();
        conditionMap.put("ids", idList);
        conditionMap.put("page", page);
        conditionMap.put("size", size);
        List<BookDto> bookList = bookMapper.queryBookByIds(conditionMap);
        return PageList.of(bookList, 0);
    }
}
