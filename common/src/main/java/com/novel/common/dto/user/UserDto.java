package com.novel.common.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable
{
    private Integer uid;
    private String nickName;
    private String userName;
    private String publicKey;
    private Integer type;
    private String autograph;
    private String nation;
    private String headImg;
    private String region;
    private String phone;
}
