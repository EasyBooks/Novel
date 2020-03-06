/*
 * 作者：刘时明
 * 时间：2020/3/5-10:53
 * 作用：
 */
package com.novel.admin.security.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TableName("t_admin")
@Data
public class Admin implements Serializable
{
    @TableId
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String roles;

    public static List<SimpleGrantedAuthority> getRoles(Admin admin)
    {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (admin.getRoles() == null)
        {
            return authorities;
        }
        Arrays.stream(admin.getRoles().split(",")).forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return authorities;
    }
}
