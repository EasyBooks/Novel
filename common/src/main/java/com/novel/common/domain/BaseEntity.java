package com.novel.common.domain;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable
{
    private Integer status;
    private Integer createTime;
    private Integer updateTime;

    public static <E extends BaseEntity> E initEntity(Class<E> clazz)
    {
        E entity = ConstructorAccess.get(clazz).newInstance();
        int now = (int) (System.currentTimeMillis() / 1000);
        entity.setStatus(1);
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        return entity;
    }
}
