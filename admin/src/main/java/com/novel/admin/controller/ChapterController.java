/*
 * 作者：刘时明
 * 时间：2020/3/3-22:42
 * 作用：
 */
package com.novel.admin.controller;

import com.novel.admin.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/chapter")
public class ChapterController
{
    @Autowired
    private ChapterService chapterService;

    public Object list()
    {
        return null;
    }
}
