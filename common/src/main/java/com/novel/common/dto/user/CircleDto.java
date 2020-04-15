package com.novel.common.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-14 17:17
 **/
@Data
public class CircleDto implements Serializable
{
    private Long id;
    private Integer uid;
    private String nickname;
    private String headImg;
    private String content;
    private List<CircleCommentDto> comments;
    private Integer likeNum;
    private Integer commentNum;
    private String createTime;
}
