package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class Comment extends BaseEntity
{
    private Long id;
    private Integer uid;
    private String content;
    private Long bookId;
    private Long replyId;
}
