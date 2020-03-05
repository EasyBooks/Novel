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
    private Integer id;
    private String nickName;
    private String userName;
    private String password;
    private String roles;

    public List<SimpleGrantedAuthority> getRoles()
    {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return authorities;
    }
}
