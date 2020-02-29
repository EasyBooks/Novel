/*
 * 作者：刘时明
 * 时间：2020/1/9-10:40
 * 作用：
 */
package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class User extends BaseEntity
{
    private Long id;
    private Integer uid;
    private String username;
    private String password;
    private String nickname;
    private String salt;
    private Integer type;
    private String publicKey;
}
