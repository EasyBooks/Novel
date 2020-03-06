/*
 * 作者：刘时明
 * 时间：2020/3/6-21:47
 * 作用：
 */
package com.novel.admin.security.bean;

import com.novel.admin.security.entity.JwtAdmin;
import com.novel.admin.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser
{
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtAdmin getCurrentUser()
    {
        return (JwtAdmin) userDetailsService.loadUserByUsername(getCurrentUserName());
    }

    private static String getCurrentUserName()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null)
        {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
