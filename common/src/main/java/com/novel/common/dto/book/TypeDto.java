/*
 * 作者：刘时明
 * 时间：2020/1/19-21:27
 * 作用：
 */
package com.novel.common.dto.book;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeDto implements Serializable
{
    private Long id;
    private Long pid;
    private String pic;
    private String name;
}
