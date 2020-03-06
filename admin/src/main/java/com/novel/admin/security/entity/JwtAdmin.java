/*
 * 作者：刘时明
 * 时间：2020/3/5-11:18
 * 作用：
 */
package com.novel.admin.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class JwtAdmin implements UserDetails
{
    private Long id;
    private String nickName;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtAdmin()
    {
    }

    public JwtAdmin(Admin admin)
    {
        this.id = admin.getId();
        this.userName = admin.getUsername();
        this.nickName = admin.getNickname();
        this.password = admin.getPassword();
        this.authorities = Admin.getRoles(admin);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
