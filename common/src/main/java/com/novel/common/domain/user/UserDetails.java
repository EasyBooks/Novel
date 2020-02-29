package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDetails extends BaseEntity
{
    private Integer uid;
    private String headImg;
    private String phone;
    private Integer loginType;
    private Integer birthday;
    private String region;
    private String nation;
    private String autograph;
}
