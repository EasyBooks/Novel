/*
 * 作者：刘时明
 * 时间：2019/11/24-22:25
 * 作用：
 */
package com.novel.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageList<E>
{
    private List<E> data;
    private int total;
    private int pageIndex;
    private int pageSize;

    public static <E> PageList<E> of(List<E> data,int total)
    {
        return new PageList(data,total,1,10);
    }

    public static <E> PageList<E> of(List<E> data,int total,int pageIndex,int pageSize)
    {
        return new PageList(data,total,pageIndex,pageSize);
    }
}
