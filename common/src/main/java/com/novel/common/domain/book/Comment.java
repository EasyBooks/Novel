package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * 评论
 */
@Data
public class Comment extends BaseEntity
{
    private Integer uid;
    private String content;
    // 哪本书的评论
    private Long bookId;
    // 回复的谁
    private Long replyId;
}
