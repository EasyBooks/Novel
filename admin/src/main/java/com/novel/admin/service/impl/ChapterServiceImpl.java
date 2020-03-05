/*
 * 作者：刘时明
 * 时间：2020/3/4-9:35
 * 作用：
 */
package com.novel.admin.service.impl;

import com.novel.admin.mapper.ChapterMapper;
import com.novel.admin.service.ChapterService;
import com.novel.common.define.Define;
import com.novel.common.domain.book.Chapter;
import com.novel.common.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChapterServiceImpl implements ChapterService
{
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private Snowflake snowflake;

    @Override
    public Map<String, Object> list(int page, int size, Map<String, Object> condition)
    {
        return null;
    }

    @Override
    public int update(Chapter chapter)
    {
        int now = (int) (System.currentTimeMillis() / 1000);
        chapter.setUpdateTime(now);
        return chapterMapper.updateById(chapter);
    }

    @Override
    public int save(Chapter chapter)
    {
        int now = (int) (System.currentTimeMillis() / 1000);
        chapter.setId(snowflake.nextId());
        chapter.setStatus(Define.ENABLE);
        chapter.setCreateTime(now);
        chapter.setUpdateTime(now);
        return chapterMapper.insert(chapter);
    }

    @Override
    public int delete(Long id)
    {
        return chapterMapper.enable(Define.DISABLE, id);
    }

    @Override
    public Chapter selectById(Long id)
    {
        return chapterMapper.selectById(id);
    }

    @Override
    public int deleteByBookId(Long bookId)
    {
        return chapterMapper.enableByBookId(Define.DISABLE, bookId);
    }
}
