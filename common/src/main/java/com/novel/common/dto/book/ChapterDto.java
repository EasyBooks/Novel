/*
 * 作者：刘时明
 * 时间：2020/4/4-14:44
 * 作用：
 */
package com.novel.common.dto.book;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChapterDto implements Serializable
{
    private String id;
    private String chapterName;
    private Integer createTime;
    // 页码
    private Integer page;
    private String content;
    // 排序值
    private Integer sorted;
    // 是否最开始的一章
    private Integer isFirst;
    // 是否最后一章
    private Integer isLast;
}
