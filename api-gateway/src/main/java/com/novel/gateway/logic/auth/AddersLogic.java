/**
 * 作者：刘时明
 * 时间：2019/11/2-17:54
 * 作用：
 */
package com.novel.gateway.logic.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class AddersLogic
{
    private static Set<String> IPWhiteSet = new HashSet<>();
    private static Set<String> IPBlackSet = new HashSet<>();

    @Value("${address.white.list}")
    private Set<String> whiteList;
    @Value("${address.black.list}")
    private Set<String> blackList;

    public static boolean isWhiteList(String ip)
    {
        return IPWhiteSet.contains(ip);
    }

    public static boolean isBlackList(String ip)
    {
        return IPBlackSet.contains(ip);
    }

    @PostConstruct
    public void AddersInit()
    {
        IPWhiteSet.addAll(whiteList);
        IPBlackSet.addAll(blackList);
    }
}
