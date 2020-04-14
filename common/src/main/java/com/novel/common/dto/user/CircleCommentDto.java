/*
 * 作者：刘时明
 * 时间：2020/4/14-22:12
 * 作用：
 */
package com.novel.common.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class CircleCommentDto implements Serializable
{
    private Long id;
    private Integer uid;
    private String nickname;
    private String headImg;
    private Long replyId;
    private String replyNickname;
    private String replyHeadImg;
    private String content;
    private String createTime;
    private Integer likeNum;
    // 回复数量
    private Integer replyNum;
}
