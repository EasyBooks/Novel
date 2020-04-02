package com.novel.gateway.logic.cache;

import com.google.gson.Gson;
import com.novel.common.dto.book.TypeDto;
import com.novel.common.dto.user.BannerDto;
import com.novel.user.service.PRCTypeService;
import com.novel.user.service.RPCBannerService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 18:43
 **/
@Component
public class RedisCacheLogic
{
    private static final Gson gson=new Gson();
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Reference(version = "1.0.0",check = false)
    private RPCBannerService bannerService;
    @Reference(version = "1.0.0",check = false)
    private PRCTypeService typeService;

    public String cacheAnGetBannerList()
    {
        String key="banner";
        String banner = redisTemplate.opsForValue().get(key);
        if(banner==null)
        {
            List<BannerDto> bannerList = bannerService.list();
            if(ObjectUtils.isEmpty(bannerList)){
                return "[]";
            }
            String json=gson.toJson(bannerList);
            redisTemplate.opsForValue().set(key,gson.toJson(bannerList),30 , TimeUnit.MINUTES);
            return json;
        }
        return banner;
    }

    public String cacheAnGetCategoryList()
    {
        String key= "category";
        String category = redisTemplate.opsForValue().get(key);
        if(category==null)
        {
            List<TypeDto> list = typeService.list();
            if(ObjectUtils.isEmpty(list)){
                return "[]";
            }
            String json=gson.toJson(list);
            redisTemplate.opsForValue().set(key,json,30 , TimeUnit.MINUTES);
            return json;
        }
        return category;
    }
}
