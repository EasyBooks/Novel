/*
 * 作者：刘时明
 * 时间：2020/3/4-9:35
 * 作用：
 */
package com.novel.admin.service;

import com.novel.common.bean.CurdService;
import com.novel.common.domain.book.Chapter;

public interface ChapterService extends CurdService<Chapter>
{
    int deleteByBookId(Long bookId);
}
