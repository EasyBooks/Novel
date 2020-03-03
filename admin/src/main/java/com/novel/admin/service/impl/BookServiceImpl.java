/*
 * 作者：刘时明
 * 时间：2020/3/3-22:47
 * 作用：
 */
package com.novel.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.novel.admin.mapper.BookMapper;
import com.novel.admin.service.BookService;
import com.novel.admin.utils.DtoUtil;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDto;
import com.novel.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Object list(int page, int size, Map<String, Object> condition)
    {
        IPage<Book> bookPage = new Page<>(page, size);
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        Object title = condition.get("title");
        if (title != null)
        {
            wrapper.like("title", title);
        }
        Object typeId = condition.get("typeId");
        if (typeId != null)
        {
            wrapper.eq("type_id", typeId);
        }
        Object startTime = condition.get("startTime");
        if (startTime != null)
        {
            wrapper.gt("create_time", startTime);
        }
        Object endTime = condition.get("endTime");
        if (endTime != null)
        {
            wrapper.lt("create_time", endTime);
        }
        Object sortType = condition.get("sortType");
        if (sortType != null)
        {
            switch ((Integer) sortType)
            {
                case 1:
                    // 点击数量升序
                    wrapper.orderByAsc("click");
                    break;
                case 2:
                    // 点击数量降序
                    wrapper.orderByDesc("click");
                    break;
                case 3:
                    // 推荐数量升序
                    wrapper.orderByAsc("recommend");
                    break;
                case 4:
                    // 推荐数量降序
                    wrapper.orderByDesc("recommend");
                    break;
                case 5:
                    // 收藏数量升序
                    wrapper.orderByAsc("collection");
                    break;
                case 6:
                    // 收藏数量降序
                    wrapper.orderByDesc("collection");
                    break;
                default:
                    break;
            }
        }
        bookMapper.selectPage(bookPage, wrapper);
        List<BookDto> bookDtoList = DtoUtil.convertBook(bookPage.getRecords());
        completionBookDto(bookDtoList);
        return ResultUtil.success(Map.of("page", bookPage.getPages(), "size", bookPage.getTotal(), "data", bookDtoList));
    }

    private void completionBookDto(List<BookDto> bookDtoList)
    {

    }
}