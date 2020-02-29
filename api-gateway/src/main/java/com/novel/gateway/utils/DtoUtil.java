/*
 * 作者：刘时明
 * 时间：2020/1/19-21:40
 * 作用：
 */
package com.novel.gateway.utils;

import com.novel.common.domain.book.Type;
import com.novel.common.dto.book.TypeDto;

import java.util.ArrayList;
import java.util.List;

public class DtoUtil
{
    public static List<TypeDto> typeDto(List<Type> typeList)
    {
        List<TypeDto> typeDtoList = new ArrayList<>(typeList.size());
        typeList.forEach(e ->
        {
            TypeDto dto = new TypeDto();
            dto.setId(e.getId());
            dto.setPid(e.getPid());
            dto.setName(e.getName());
            dto.setPic(e.getPic());
            typeDtoList.add(dto);
        });
        return typeDtoList;
    }
}
