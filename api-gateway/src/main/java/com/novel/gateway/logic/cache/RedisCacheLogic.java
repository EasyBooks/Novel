package com.novel.gateway.logic.cache;

import com.google.gson.Gson;
import com.novel.common.bean.PageList;
import com.novel.common.dto.book.BookDetailDto;
import com.novel.common.dto.book.BookDto;
import com.novel.common.dto.book.ChapterDto;
import com.novel.common.dto.book.TypeDto;
import com.novel.common.dto.user.AuthorDto;
import com.novel.common.dto.user.BannerDto;
import com.novel.common.dto.user.CircleDto;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.handler.BookHandler;
import com.novel.user.service.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
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
    private static final Gson gson = new Gson();
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Reference(version = "1.0.0", check = false)
    private RPCBannerService bannerService;
    @Reference(version = "1.0.0", check = false)
    private PRCTypeService typeService;
    @Reference(version = "1.0.0", check = false)
    private RPCBookService bookService;
    @Reference(version = "1.0.0", check = false)
    private RPCUserService userService;
    @Reference(version = "1.0.0", check = false)
    private RPCChapterService chapterService;
    @Reference(version = "1.0.0", check = false)
    private RPCCircleService circleService;

    private static final Random RANDOM = new Random();
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final String EMPTY_RESULT = "{\"code\":0,\"msg\":\"ok\",\"data\":[]}";

    public String cacheAnGetBannerList()
    {
        String key = "banner";
        String banner = redisTemplate.opsForValue().get(key);
        if (banner == null)
        {
            try
            {
                LOCK.lock();
                String value = redisTemplate.opsForValue().get(key);
                if (value != null) return value;
                List<BannerDto> bannerList = bannerService.list();
                if (ObjectUtils.isEmpty(bannerList)) return EMPTY_RESULT;
                String json = gson.toJson(ResultUtil.success(bannerList));
                redisTemplate.opsForValue().set(key, gson.toJson(bannerList), 20 + RANDOM.nextInt(20), TimeUnit.MINUTES);
                return json;
            } finally
            {
                LOCK.unlock();
            }
        }
        return banner;
    }

    public String cacheAnGetCategoryList()
    {
        String key = "category";
        String category = redisTemplate.opsForValue().get(key);
        if (category == null)
        {
            try
            {
                LOCK.lock();
                String value = redisTemplate.opsForValue().get(key);
                if (value != null) return value;
                List<TypeDto> list = typeService.list();
                if (ObjectUtils.isEmpty(list)) return EMPTY_RESULT;
                String json = gson.toJson(ResultUtil.success(list));
                redisTemplate.opsForValue().set(key, json, 20 + RANDOM.nextInt(20), TimeUnit.MINUTES);
                return json;
            } finally
            {
                LOCK.unlock();
            }
        }
        return category;
    }

    public String cacheAnGetBoutique()
    {
        String key = "boutique";
        String indexBook = redisTemplate.opsForValue().get(key);
        if (indexBook == null)
        {
            try
            {
                LOCK.lock();
                String value = redisTemplate.opsForValue().get(key);
                if (value != null) return value;
                Map<String, List<BookDto>> boutiqueMap = bookService.boutiqueList();
                boutiqueMap.forEach((k,v)->BookHandler.makeUpBookDto(userService,v));
                if (ObjectUtils.isEmpty(boutiqueMap)) return EMPTY_RESULT;
                String json = gson.toJson(ResultUtil.success(boutiqueMap));
                redisTemplate.opsForValue().set(key, json, 20 + RANDOM.nextInt(20), TimeUnit.MINUTES);
                return json;
            } finally
            {
                LOCK.unlock();
            }
        }
        return indexBook;
    }

    public String cacheAnGetDetail(Long id)
    {
        String key=String.format("book_detail_%d",id);
        String detail = redisTemplate.opsForValue().get(key);
        if (detail == null)
        {
            try
            {
                LOCK.lock();
                String value = redisTemplate.opsForValue().get(key);
                if (value != null) return value;
                BookDetailDto bookDetail = bookService.bookDetail(id);
                PageList<CircleDto> circlePage = circleService.findListByBookId(id, 0, 5);
                bookDetail.setCircleList(circlePage.getData());
                List<Long> idList=new ArrayList<>(1);
                idList.add(Long.parseLong(bookDetail.getId()));
                List<AuthorDto> authors = userService.findAuthors(idList);
                bookDetail.setAuthors(authors);
                if(!ObjectUtils.isEmpty(authors))
                {
                    StringBuilder builder=new StringBuilder();
                    authors.forEach(e->builder.append(e.getNickname()).append(","));
                    builder.delete(builder.length()-1,builder.length());
                    bookDetail.setAuthor(builder.toString());
                }
                String json = gson.toJson(ResultUtil.success(bookDetail));
                redisTemplate.opsForValue().set(key, json, 20 + RANDOM.nextInt(20), TimeUnit.MINUTES);
                return json;
            } finally
            {
                LOCK.unlock();
            }
        }
        return detail;
    }

    public String cacheAnGetChapters(Long bookId)
    {
        String key = String.format("chapters_%d",bookId);
        String chapters = redisTemplate.opsForValue().get(key);
        if (chapters == null)
        {
            try
            {
                LOCK.lock();
                String value = redisTemplate.opsForValue().get(key);
                if (value != null) return value;
                List<ChapterDto> chapterList = chapterService.chapterList(bookId);
                if (ObjectUtils.isEmpty(chapterList)) return EMPTY_RESULT;
                String json = gson.toJson(ResultUtil.success(chapterList));
                redisTemplate.opsForValue().set(key, json, 20 + RANDOM.nextInt(20), TimeUnit.MINUTES);
                return json;
            } finally
            {
                LOCK.unlock();
            }
        }
        return chapters;
    }
}
