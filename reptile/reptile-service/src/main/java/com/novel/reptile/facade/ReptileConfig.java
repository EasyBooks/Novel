/*
 * 作者：刘时明
 * 时间：2020/3/29-21:36
 * 作用：
 */
package com.novel.reptile.facade;

import lombok.Data;

@Data
public class ReptileConfig
{
    /**
     * 纵横支持小说ID区间全量爬取
     */
    private int start;
    private int end;

    /**
     * 金庸网支持小说名称定向爬取
     */
    private String name;
}
