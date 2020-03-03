/*
 * 作者：刘时明
 * 时间：2020/3/4-0:58
 * 作用：
 */
package com.novel.admin.controller;

import com.novel.admin.service.TypeService;
import com.novel.common.domain.book.Type;
import com.novel.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/type")
public class TypeController
{
    @Autowired
    private TypeService typeService;

    @PostMapping("save")
    public Object save(Type type)
    {
        return typeService.saveOrUpdateType(type);
    }

    @PostMapping("delete")
    public Object delete(Long id)
    {
        if (id == null)
        {
            return ResultUtil.error("ID不可以为空");
        }
        return typeService.deleteType(id);
    }
}
