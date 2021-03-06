/*
 * 作者：刘时明
 * 时间：2020/3/4-0:45
 * 作用：
 */
package com.novel.common.dto.book;

import com.novel.common.dto.user.AuthorDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookDto implements Serializable
{
    private String id;
    // 分类
    private String typeId;
    private String typeName;
    private String title;
    private String synopsis;
    private String cover;
    private Integer recommend;
    private Integer click;
    private Integer collection;
    private Integer instalments;
    private String wordNum;
    private Integer createTime;
    private List<AuthorDto> authors;
    private String remake;
    private String author;
}
