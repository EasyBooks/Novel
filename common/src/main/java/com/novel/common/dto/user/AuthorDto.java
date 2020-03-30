/*
 * 作者：刘时明
 * 时间：2020/3/28-23:07
 * 作用：
 */
package com.novel.common.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorDto implements Serializable
{
    private Integer uid;
    private Long bookId;
    private String nickname;
    private String headImg;
}
