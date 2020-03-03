/*
 * 作者：刘时明
 * 时间：2020/3/4-0:58
 * 作用：
 */
package com.novel.admin.service;

import com.novel.common.domain.book.Type;

import java.util.Map;

public interface TypeService
{
    Object list(Map<String, Object> condition);

    Object saveOrUpdateType(Type type);

    Object deleteType(Long id);
}
