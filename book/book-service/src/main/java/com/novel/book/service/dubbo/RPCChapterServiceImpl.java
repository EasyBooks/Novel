/*
 * 作者：刘时明
 * 时间：2020/4/4-17:33
 * 作用：
 */
package com.novel.book.service.dubbo;

import com.novel.book.service.ChapterService;
import com.novel.common.domain.book.Chapter;
import com.novel.common.dto.book.ChapterDto;
import com.novel.common.dto.book.PageDto;
import com.novel.user.service.RPCChapterService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(version = "99.0.0",timeout = 6000)
public class RPCChapterServiceImpl implements RPCChapterService
{
    @Autowired
    private ChapterService chapterService;

    @Override
    public List<ChapterDto> chapterList(Long bookId)
    {
        return chapterService.chapterList(bookId);
    }

    @Override
    public ChapterDto read(Long id,Integer uid)
    {
        return chapterService.read(id,uid);
    }

    @Override
    public PageDto readByPage(Long id, Integer page) {
        return chapterService.readByPage(id, page);
    }
}
