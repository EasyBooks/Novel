package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * @program: Novel
 * @description: 圈子评论
 * @author: lsm
 * @create: 2020-04-14 17:20
 **/
@Data
public class CircleComment extends BaseEntity
{
    private Long circleId;
    private Integer uid;
    private Integer replyUid;
    private String content;
}
