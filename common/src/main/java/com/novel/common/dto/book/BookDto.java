/*
 * 作者：刘时明
 * 时间：2020/3/4-0:45
 * 作用：
 */
package com.novel.common.dto.book;

import com.novel.common.domain.user.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookDto implements Serializable
{
    private Long id;
    private Long typeId;
    private String typeName;
    private String title;
    private String synopsis;
    private String cover;
    private Integer recommend;
    private Integer click;
    private Integer collection;
    private Integer instalments;
    private Long wordNum;
    private Integer createTime;
    private List<User> authors;
}
