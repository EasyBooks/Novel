/*
 * 作者：刘时明
 * 时间：2020/4/4-14:49
 * 作用：
 */
package com.novel.common.dto.book;

import lombok.Data;

import java.io.Serializable;

@Data
public class CircleDto implements Serializable
{
    private String id;
    private Integer uid;
    private String nickname;
    private String headImg;
    private String bookId;
    private String bookTitle;
    private String content;
    private Integer lickNum;
    private Integer commentNum;
}
