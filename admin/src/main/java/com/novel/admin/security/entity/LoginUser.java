/*
 * 作者：刘时明
 * 时间：2020/3/5-11:33
 * 作用：
 */
package com.novel.admin.security.entity;

import lombok.Data;

@Data
public class LoginUser
{
    private String username;
    private String password;
    private Boolean rememberMe;
}
