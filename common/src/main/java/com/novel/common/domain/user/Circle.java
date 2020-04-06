/*
 * 作者：刘时明
 * 时间：2020/4/4-14:46
 * 作用：
 */
package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

/**
 * 圈子，简单理解为说说
 */
@Data
public class Circle  extends BaseEntity
{
    // 谁发的？
    private Integer uid;
    // 关联小说
    private Long bookId;
    // 说说内容
    private String content;
}
