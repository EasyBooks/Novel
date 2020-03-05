/*
 * 作者：刘时明
 * 时间：2020/3/4-0:58
 * 作用：
 */
package com.novel.admin.service;

import com.novel.common.bean.CurdService;
import com.novel.common.domain.book.Type;

public interface TypeService extends CurdService<Type>
{
    String findTypeName(Long id);
}
