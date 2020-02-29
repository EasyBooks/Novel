/*
 * 作者：刘时明
 * 时间：2020/1/19-18:53
 * 作用：
 */
package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class Chapter extends BaseEntity
{
    private Long id;
    private Long bookId;
    private Integer sorted;
    private String name;
    private String content;
}
