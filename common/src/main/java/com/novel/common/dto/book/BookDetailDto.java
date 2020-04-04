/*
 * 作者：刘时明
 * 时间：2020/4/4-14:39
 * 作用：
 */
package com.novel.common.dto.book;

import com.novel.common.dto.user.AuthorDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookDetailDto implements Serializable
{
    private String id;
    private String title;
    private String synopsis;
    private String cover;
    private Integer click;
    private Integer collection;
    private Integer instalments;
    private Integer top;
    private List<CircleDto> circleList;
    private List<AuthorDto> authors;
    private String author;
    private String lastChapter;
    // 最后更新时间
    private Integer updated;
    // 是否完结
    private Integer isSerial;
    // 相关推荐
    private List<BookDto> related;
}
