/*
 * 作者：刘时明
 * 时间：2020/1/19-20:38
 * 作用：
 */
package com.novel.book.utils;

import com.novel.common.domain.book.Type;

import java.util.*;

public class TypeParentUtil
{
    // [{1:"电子3C",[{3:"手机",[{11:"三星"},{12:"苹果"}]},{10,"电脑"}]}]
    public static List<Object> getParentType(List<Type> typeList)
    {
        List<Object> result = new ArrayList<>(typeList.size());
        Map<Long, Type> idMap = new HashMap<>(typeList.size());
        typeList.forEach(e ->
        {
            idMap.put(e.getId(), e);
        });
        int level = 0;
        while (typeList.size() > 0)
        {
            Iterator<Type> iterator = typeList.iterator();
            while (iterator.hasNext())
            {
                Type temp = iterator.next();
                if (getParentLevel(temp.getId(), idMap) == level)
                {
                    iterator.remove();
                }
            }
            level++;
        }
        return result;
    }

    private static int getParentLevel(Long id, Map<Long, Type> idMap)
    {
        return 0;
    }
}
