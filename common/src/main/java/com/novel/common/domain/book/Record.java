/*
 * 作者：刘时明
 * 时间：2020/1/19-22:07
 * 作用：
 */
package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * 阅读记录
 */
@Data
public class Record extends BaseEntity
{
    private Integer uid;
    private Long bookId;
    private Long chapterId;
    private Integer page;
}
