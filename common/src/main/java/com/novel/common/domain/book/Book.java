package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * 小说
 */
@Data
public class Book extends BaseEntity
{
    private Long typeId;
    private String title;
    private String synopsis;
    private String cover;
    private Integer recommend;
    private Integer click;
    private Integer collection;
    private Integer instalments;
    private Long wordNum;
    private Long thirdId;
    private Integer platformId;
    private Integer isSerial;
}
