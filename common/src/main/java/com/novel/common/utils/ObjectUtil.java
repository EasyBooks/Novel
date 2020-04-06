package com.novel.common.utils;

public class ObjectUtil
{
    public static boolean isNull(String... args)
    {
        for (String o:args)
        {
            if(o==null||o.equals(""))
            {
                return true;
            }
        }
        return false;
    }
}
