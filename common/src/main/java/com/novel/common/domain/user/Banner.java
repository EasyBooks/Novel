package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 18:29
 **/
@Data
public class Banner extends BaseEntity
{
    private Long id;
    private String img;
    private String link;
    private String title;
    private Integer enable;
}
