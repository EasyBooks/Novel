package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class Type extends BaseEntity
{
    private Long id;
    private Long pid;
    private String pic;
    private String name;
}
