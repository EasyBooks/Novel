/*
 * 作者：刘时明
 * 时间：2020/1/19-18:50
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.dto.book.ChapterDto;

import java.util.List;

public interface RPCChapterService
{
    List<ChapterDto> chapterList(Long bookId);

    ChapterDto read(Long id,Integer uid);
}
