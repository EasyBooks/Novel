/*
 * 作者：刘时明
 * 时间：2020/1/19-22:01
 * 作用：
 */
package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * 小说-作者
 */
@Data
public class BookAuthor extends BaseEntity
{
    private Integer userId;
    private Integer bookId;
}
