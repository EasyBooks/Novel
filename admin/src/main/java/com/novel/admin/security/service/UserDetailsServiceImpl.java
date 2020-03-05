/*
 * 作者：刘时明
 * 时间：2020/3/5-11:16
 * 作用：
 */
package com.novel.admin.security.service;

import com.novel.admin.security.entity.Admin;
import com.novel.admin.security.entity.JwtAdmin;
import com.novel.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService
{
    @Autowired
    AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        Admin admin = adminService.findUserByUserName(userName);
        return new JwtAdmin(admin);
    }
}
