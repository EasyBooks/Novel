/*
 * 作者：刘时明
 * 时间：2020/3/29-13:03
 * 作用：
 */
package com.novel.user.service.impl;

import com.novel.common.bean.PageList;
import com.novel.common.domain.BaseEntity;
import com.novel.common.domain.user.Collection;
import com.novel.common.bean.Snowflake;
import com.novel.user.mapper.CollectionMapper;
import com.novel.user.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService
{
    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public PageList<Long> collectionBookIds(Integer uid, int page, int size)
    {
        return PageList.of(collectionMapper.collectionBookIds(uid, page, size), collectionMapper.countCollection(uid));
    }

    @Override
    public int saveCollection(Integer uid, Integer type, Long bookId)
    {
        Collection collection = BaseEntity.initEntity(Collection.class);
        collection.setBookId(bookId);
        collection.setId(snowflake.nextId());
        collection.setType(type);
        return collectionMapper.insert(collection);
    }

    @Override
    public int deleteCollection(Long id)
    {
        Collection collection = collectionMapper.selectById(id);
        if (collection == null) return 0;
        collection.setStatus(0);
        return collectionMapper.updateById(collection);
    }
}
