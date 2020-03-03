/*
 * 作者：刘时明
 * 时间：2020/3/4-0:58
 * 作用：
 */
package com.novel.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.admin.mapper.TypeMapper;
import com.novel.admin.service.TypeService;
import com.novel.common.domain.book.Type;
import com.novel.common.utils.ResultUtil;
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
    public Object list(Map<String, Object> condition)
    {
        return null;
    }

    @Override
    public Object saveOrUpdateType(Type type)
    {
        int result;
        int now = (int) (System.currentTimeMillis() / 1000);
        type.setUpdateTime(now);
        if (type.getId() == null)
        {
            type.setId(snowflake.nextId());
            type.setStatus(1);
            type.setCreateTime(now);
            result = typeMapper.insert(type);
        } else
        {
            result = typeMapper.updateById(type);
        }
        if (result == 1)
        {
            return ResultUtil.success("ok");
        }
        return ResultUtil.error("error");
    }

    @Override
    public Object deleteType(Long id)
    {
        if (deleteTypeById(id) == 1)
        {
            return ResultUtil.success("ok");
        }
        return ResultUtil.error("找不到删除对象");
    }

    private int deleteTypeById(Long id)
    {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        if (typeMapper.selectCount(wrapper) == 0)
        {
            return typeMapper.deleteById(id);
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
}
