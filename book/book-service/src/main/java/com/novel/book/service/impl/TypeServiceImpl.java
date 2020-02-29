/*
 * 作者：刘时明
 * 时间：2020/1/19-19:38
 * 作用：
 */
package com.novel.book.service.impl;

import com.novel.book.mapper.TypeMapper;
import com.novel.book.service.TypeService;
import com.novel.book.utils.ConditionUtil;
import com.novel.common.domain.book.Type;
import com.novel.common.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService
{
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private Snowflake snowflake;

    @Override
    public List<Type> typeList(Map<String, Object> conditionMap)
    {
        return typeMapper.selectByMap(ConditionUtil.getTypeCondition(conditionMap));
    }

    @Transactional
    @Override
    public boolean updateType(Type type)
    {
        int now = (int) (System.currentTimeMillis() / 1000);
        type.setUpdateTime(now);
        if (type.getId() == null)
        {
            type.setId(snowflake.nextId());
            type.setCreateTime(now);
            return typeMapper.insert(type) == 1;
        } else
        {
            return typeMapper.updateById(type) == 1;
        }
    }

    @Override
    public boolean deleteType(long id)
    {
        return typeMapper.deleteById(id) == 1;
    }

    @Override
    public Type findType(long id)
    {
        return typeMapper.selectById(id);
    }
}
