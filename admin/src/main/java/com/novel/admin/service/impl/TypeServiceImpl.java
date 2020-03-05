/*
 * 作者：刘时明
 * 时间：2020/3/4-0:58
 * 作用：
 */
package com.novel.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.admin.mapper.TypeMapper;
import com.novel.admin.service.TypeService;
import com.novel.common.define.Define;
import com.novel.common.domain.book.Type;
import com.novel.common.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Map<String, Object> list(int page, int size, Map<String, Object> condition)
    {
        return null;
    }

    @Override
    public int update(Type type)
    {
        int now = (int) (System.currentTimeMillis() / 1000);
        type.setUpdateTime(now);
        return typeMapper.updateById(type);
    }

    @Override
    public int save(Type type)
    {
        int now = (int) (System.currentTimeMillis() / 1000);
        type.setId(snowflake.nextId());
        type.setStatus(Define.ENABLE);
        type.setCreateTime(now);
        type.setUpdateTime(now);
        return typeMapper.insert(type);
    }

    @Override
    public int delete(Long id)
    {
        return deleteTypeById(id);
    }

    @Override
    public Type selectById(Long id)
    {
        return null;
    }

    private int deleteTypeById(Long id)
    {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        if (typeMapper.selectCount(wrapper) == 0)
        {
            return typeMapper.enable(Define.DISABLE, id);
        } else
        {
            wrapper.comment("id");
            List<Type> types = typeMapper.selectList(wrapper);
            for (Type t : types)
            {
                return deleteTypeById(t.getId());
            }
        }
        return 0;
    }

    @Override
    public String findTypeName(Long id)
    {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        Type t = typeMapper.selectOne(wrapper.select("name").eq("id", id));
        return t == null ? null : t.getName();
    }
}
