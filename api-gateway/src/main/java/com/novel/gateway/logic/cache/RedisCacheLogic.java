package com.novel.gateway.logic.cache;

import com.google.gson.Gson;
import com.novel.common.dto.book.TypeDto;
import com.novel.common.dto.user.BannerDto;
import com.novel.common.utils.ResultUtil;
import com.novel.user.service.PRCTypeService;
import com.novel.user.service.RPCBannerService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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

    private static final Random RANDOM=new Random();
    private static final ReentrantLock LOCK=new ReentrantLock();
    private static final String EMPTY_RESULT="{\"code\":0,\"msg\":\"ok\",\"data\":[]}";

    public String cacheAnGetBannerList()
    {
        String key="banner";
        String banner = redisTemplate.opsForValue().get(key);
        if(banner==null)
        {
            try {
                LOCK.lock();
                String value=redisTemplate.opsForValue().get(key);
                if(value!=null) return value;
                List<BannerDto> bannerList = bannerService.list();
                if(ObjectUtils.isEmpty(bannerList)) return EMPTY_RESULT;
                String json=gson.toJson(ResultUtil.success(bannerList));
                redisTemplate.opsForValue().set(key,gson.toJson(bannerList),20+RANDOM.nextInt(20) , TimeUnit.MINUTES);
                return json;
            }finally {
                LOCK.unlock();
            }
        }
        return banner;
    }

    public String cacheAnGetCategoryList()
    {
        String key= "category";
        String category = redisTemplate.opsForValue().get(key);
        if(category==null)
        {
            try {
                LOCK.lock();
                String value=redisTemplate.opsForValue().get(key);
                if(value!=null) return value;
                List<TypeDto> list = typeService.list();
                if(ObjectUtils.isEmpty(list)) return EMPTY_RESULT;
                String json=gson.toJson(ResultUtil.success(list));
                redisTemplate.opsForValue().set(key,json,20+RANDOM.nextInt(20) , TimeUnit.MINUTES);
                return json;
            }finally {
                LOCK.unlock();
            }
        }
        return category;
    }
}
