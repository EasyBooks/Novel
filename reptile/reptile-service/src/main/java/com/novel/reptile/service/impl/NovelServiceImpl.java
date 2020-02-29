package com.novel.reptile.service.impl;

import com.novel.common.domain.BaseEntity;
import com.novel.common.domain.book.Book;
import com.novel.common.utils.Snowflake;
import com.novel.reptile.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovelServiceImpl implements NovelService
{
    @Autowired
    private Snowflake snowflake;

    @Override
    public List<String> search(String keyword, int size)
    {
        Book book= BaseEntity.initEntity(Book.class);
        book.setId(snowflake.nextId());

        return null;
    }
}
