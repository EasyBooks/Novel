package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * @program: Novel
 * @description: 圈子点赞
 * @author: lsm
 * @create: 2020-04-14 17:24
 **/
@Data
public class CircleLike extends BaseEntity
{
    private Integer uid;
    // 1赞，0踩
    private Integer type;
    private Long circleId;
}
