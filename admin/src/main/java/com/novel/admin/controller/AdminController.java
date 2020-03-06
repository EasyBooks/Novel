/*
 * 作者：刘时明
 * 时间：2020/3/6-21:43
 * 作用：
 */
package com.novel.admin.controller;

import com.novel.admin.security.entity.Admin;
import com.novel.admin.service.AdminService;
import com.novel.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AdminController
{
    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public Object registerUser(@RequestBody Admin admin)
    {
        adminService.saveAdmin(admin);
        return ResultUtil.success("ok");
    }
}
