/*
 * 作者：刘时明
 * 时间：2020/3/3-22:45
 * 作用：
 */
package com.novel.admin.service;

import java.util.Map;

public interface BookService
{
    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Object list(int page, int size, Map<String,Object> condition);
}
