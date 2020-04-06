/*
 * 作者：刘时明
 * 时间：2020/4/4-17:20
 * 作用：
 */
package com.novel.book.service.impl;

import com.novel.book.mapper.BookMapper;
import com.novel.book.mapper.ChapterMapper;
import com.novel.book.mapper.RecordMapper;
import com.novel.book.service.ChapterService;
import com.novel.common.domain.book.Chapter;
import com.novel.common.dto.book.ChapterDto;
import com.novel.common.dto.book.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ChapterServiceImpl implements ChapterService
{
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<ChapterDto> chapterList(Long bookId)
    {
        return chapterMapper.findChapterList(bookId);
    }

    @Override
    public ChapterDto read(Long bookId,Integer uid)
    {
        Long chapterId=null;
        if(uid!=null)
        {
            // 查询阅读记录
            chapterId = recordMapper.findChapterRecord(bookId, uid);
        }
        if(chapterId==null)
        {
            chapterId=chapterMapper.findFirstChapter(bookId);
            if(chapterId==null) return null;
        }
        ChapterDto chapter=chapterMapper.findRead(chapterId);
        if(chapter==null)return null;
        if(Integer.valueOf(1).equals(chapter.getSorted()))
        {
            chapter.setIsFirst(1);
        }else if(chapter.getSorted().equals(chapter.getIsLast()))
        {
            chapter.setIsLast(1);
        }else
        {
            chapter.setIsLast(0);
            chapter.setIsFirst(0);
        }
        return chapter;
    }

    @Override
    public PageDto readByPage(Long id, Integer page)
    {
        return chapterMapper.readByPage(id,page);
    }
}
