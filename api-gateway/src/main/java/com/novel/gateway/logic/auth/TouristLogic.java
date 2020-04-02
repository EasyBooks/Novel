package com.novel.gateway.logic.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 18:34
 **/
@Component
public class TouristLogic
{
    private static Set<String> BaseUrls = new HashSet<>();

    @Value("${base.url}")
    private Set<String> baseUrls;

    public static boolean isTouristUrl(String url)
    {
        for (String u:BaseUrls)
        {
            if(url.startsWith(u))
            {
                return true;
            }
        }
        return false;
    }

    @PostConstruct
    public void TouristInit()
    {
        BaseUrls.addAll(baseUrls);
    }
}
