/*
 * 作者：刘时明
 * 时间：2020/3/5-11:06
 * 作用：
 */
package com.novel.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.novel.admin.security.entity.Admin;
import com.novel.admin.mapper.AdminMapper;
import com.novel.admin.service.AdminService;
import com.novel.common.bean.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService
{
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Snowflake snowflake;

    @Override
    public Admin findUserByUserName(String userName)
    {
        return adminMapper.findByUsername(userName);
    }

    @Override
    public IPage<Admin> adminList(int page, int size)
    {
        IPage<Admin> adminPage = new Page<>(page, size);
        return adminMapper.selectPage(adminPage, null);
    }

    @Override
    public int saveAdmin(Admin admin)
    {
        admin.setId(snowflake.nextId());
        admin.setRoles("DEV,PM");
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        return adminMapper.insert(admin);
    }

    @Override
    public int deleteAdmin(Long id)
    {
        return adminMapper.deleteById(id);
    }
}
