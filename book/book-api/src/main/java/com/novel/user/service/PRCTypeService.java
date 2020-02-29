/*
 * 作者：刘时明
 * 时间：2020/1/19-18:40
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.domain.book.Type;

import java.util.List;
import java.util.Map;

public interface PRCTypeService
{
    /**
     * 获取分类列表
     * @return
     */
    List<Type> typeList(Map<String,Object> conditionMap);

    /**
     * 修改分类
     * @param type
     * @return
     */
    boolean updateType(Type type);

    /**
     * 删除分类
     * @param id
     * @return
     */
    boolean deleteType(long id);

    /**
     * 获取分类
     * @param id
     * @return
     */
    Type findType(long id);
}
