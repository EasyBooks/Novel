package com.novel.common.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable
{
    private Integer uid;
    private String nickname;
    private Integer type;
    private String username;
    private String autograph;
    private String nation;
    private String headImg;
    private String region;
    private String phone;
}
