/*
 * 作者：刘时明
 * 时间：2020/3/3-22:31
 * 作用：
 */
package com.novel.admin.controller;

import com.novel.admin.service.BookService;
import com.novel.admin.utils.ParamUtil;
import com.novel.common.domain.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin/book")
public class BookController
{
    @Autowired
    private BookService bookService;

    @GetMapping("list")
    public Object list(Integer page, Integer size, String title,
                       Long typeId, Integer startTime, Integer endTime, Integer sortType)
    {
        Integer[] params = ParamUtil.PageVerify(page, size);
        Map<String, Object> condition = new HashMap<>();
        if (title != null)
        {
            condition.put("title", title);
        }
        if (typeId != null)
        {
            condition.put("typeId", typeId);
        }
        if (startTime != null)
        {
            condition.put("startTime", startTime);
        }
        if (endTime != null)
        {
            condition.put("endTime", endTime);
        }
        if (sortType != null)
        {
            condition.put("sortType", sortType);
        }
        return bookService.list(params[0], params[1], condition);
    }
}
