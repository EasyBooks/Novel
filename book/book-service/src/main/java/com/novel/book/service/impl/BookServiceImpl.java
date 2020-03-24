/*
 * 作者：刘时明
 * 时间：2020/1/19-19:38
 * 作用：
 */
package com.novel.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.novel.book.mapper.BookMapper;
import com.novel.book.service.BookService;
import com.novel.book.utils.ConditionUtil;
import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Book;
import com.novel.common.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PageList<Book> bookList(Map<String, Object> conditionMap, int page, int size)
    {
        IPage<Book> pageObj = new Page<>(page, size);
        IPage<Book> iPage=bookMapper.selectPage(pageObj, ConditionUtil.getWrapperByMap(conditionMap));
        return PageList.of(iPage.getRecords(),iPage.getTotal());
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
    public PageList<Book> findCollection(int uid, int page, int size)
    {
        IPage<Book> pageObj = new Page<>(page, size);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        IPage<Book> iPage=bookMapper.selectPage(pageObj, queryWrapper);
        return PageList.of(iPage.getRecords(),iPage.getTotal());
    }
}
