/*
 * 作者：刘时明
 * 时间：2020/3/1-21:54
 * 作用：
 */
package com.novel.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/user")
public class UserController
{
    @GetMapping("test")
    public Object test(String name)
    {
        return "hello:"+name;
    }
}
