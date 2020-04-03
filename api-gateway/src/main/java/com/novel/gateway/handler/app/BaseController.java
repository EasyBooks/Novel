package com.novel.gateway.handler.app;

import com.novel.gateway.logic.cache.RedisCacheLogic;
import com.novel.gateway.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 18:31
 **/
@RestController
@RequestMapping("api/app/base")
public class BaseController
{
    @Autowired
    private RedisCacheLogic redisCacheLogic;

    /**
     * 轮播图
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("banner")
    public void banner(HttpServletResponse response) throws IOException
    {
        ResponseUtil.json(response,redisCacheLogic.cacheAnGetBannerList());
    }

    /**
     * 分类
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("category")
    public void category(HttpServletResponse response) throws IOException
    {
        ResponseUtil.json(response,redisCacheLogic.cacheAnGetCategoryList());
    }
}
