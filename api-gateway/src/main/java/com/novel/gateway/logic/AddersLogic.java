/**
 * 作者：刘时明
 * 时间：2019/11/2-17:54
 * 作用：
 */
package com.novel.gateway.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class AddersLogic
{
    private static Set IPWhiteList = new HashSet();
    private static Set IPBlackList = new HashSet();

    @Value("${address.white.list}")
    private Set whiteList;
    @Value("${address.black.list}")
    private Set blackList;

    public static boolean isWhiteList(String ip)
    {
        return IPWhiteList.contains(ip);
    }

    public static boolean isBlackList(String ip)
    {
        return IPBlackList.contains(ip);
    }

    @PostConstruct
    public void AddersInit()
    {
        IPWhiteList.addAll(whiteList);
        IPBlackList.addAll(blackList);
    }
}
