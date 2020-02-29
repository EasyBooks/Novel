package com.novel.common.utils;

public class ObjectUtil
{
    public static boolean isNull(Object... args)
    {
        for (Object o:args)
        {
            if(o==null)
            {
                return false;
            }
        }
        return true;
    }
}
