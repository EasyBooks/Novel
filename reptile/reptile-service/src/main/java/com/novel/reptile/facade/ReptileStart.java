/*
 * 作者：刘时明
 * 时间：2020/3/29-21:24
 * 作用：
 */
package com.novel.reptile.facade;

/**
 * 资源爬虫顶层接口
 */
public interface ReptileStart
{
    /**
     * 开始方法
     */
    void start();

    /**
     * 资源类型
     * @return
     */
    ReptileType type();

    /**
     * 初始化
     */
    void init(ReptileConfig config);
}
