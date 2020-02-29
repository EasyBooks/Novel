/*
 * 作者：刘时明
 * 时间：2020/1/19-18:50
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.domain.book.Chapter;

import java.util.List;

public interface RPCChapterService
{
    List<Chapter> chapterList();
}
