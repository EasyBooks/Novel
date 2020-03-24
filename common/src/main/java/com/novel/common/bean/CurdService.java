/*
 * 作者：刘时明
 * 时间：2020/3/4-9:42
 * 作用：
 */
package com.novel.common.bean;

import java.util.List;
import java.util.Map;

public interface CurdService<E>
{
    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param condition
     * @return
     */
    PageList<E> list(int page, int size, Map<String, Object> condition);

    /**
     * 查询所有
     *
     * @return
     */
    List<E> findAll();

    /**
     * 更新
     * @param entity
     * @return
     */
    int update(E entity);

    /**
     * 保存
     * @param entity
     * @return
     */
    int save(E entity);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 查询
     * @param id
     * @return
     */
    E selectById(Long id);
}
