package com.novel.common.domain;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.google.gson.annotations.Expose;
import com.novel.common.bean.Snowflake;
import com.novel.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable
{
    private Long id;
    @Expose private Integer status;
    private Integer createTime;
    @Expose private Integer updateTime;

    public static <E extends BaseEntity> E initEntity(Class<E> clazz)
    {
        E entity = ConstructorAccess.get(clazz).newInstance();
        int now = DateUtil.nowTime();
        entity.setStatus(1);
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        return entity;
    }

    public static void initEntity(BaseEntity entity, Snowflake snowflake)
    {
        int now = DateUtil.nowTime();
        entity.setId(snowflake.nextId());
        entity.setStatus(1);
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
    }

    public static void initEntity(BaseEntity entity)
    {
        int now = DateUtil.nowTime();
        entity.setStatus(1);
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
    }
}
