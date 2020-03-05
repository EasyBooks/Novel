/*
 * 作者：刘时明
 * 时间：2020/3/5-11:03
 * 作用：
 */
package com.novel.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.novel.admin.security.entity.Admin;

public interface AdminService
{
    Admin findUserByUserName(String userName);

    IPage<Admin> adminList(int page,int size);

    int saveAdmin(Admin admin);

    int deleteAdmin(Long id);
}
