/*
 * 作者：刘时明
 * 时间：2020/1/19-22:08
 * 作用：
 */
package com.novel.common.domain.book;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * 标签
 */
@Data
public class Label extends BaseEntity
{
    private String name;
}
