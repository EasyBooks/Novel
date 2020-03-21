/*
 * 作者：刘时明
 * 时间：2020/3/3-22:31
 * 作用：
 */
package com.novel.admin.controller;

import com.novel.admin.security.bean.CurrentUser;
import com.novel.admin.service.BookService;
import com.novel.admin.utils.ParamUtil;
import com.novel.common.define.Define;
import com.novel.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin/book")
@Slf4j
public class BookController
{
    @Autowired
    private BookService bookService;

    @Autowired
    private CurrentUser currentUser;

    @GetMapping("list")
    @PreAuthorize("hasAnyRole('ROLE_DEV','ROLE_PM','ROLE_ADMIN')")
    public Object list(Integer page, Integer size, String title,
                       Long typeId, Integer startTime, Integer endTime, Integer sortType)
    {
        System.out.println("访问用户="+currentUser.getCurrentUser().getNickName());
        Integer[] params = ParamUtil.PageVerify(page, size);
        Map<String, Object> condition = Define.conditionMap();
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
        return ResultUtil.success(bookService.list(params[0], params[1], condition));
    }
}
