package com.novel.gateway.handler.app;

import com.novel.gateway.logic.cache.RedisCacheLogic;
import com.novel.gateway.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-03 19:03
 **/
@RestController
@RequestMapping("api/app/book")
public class BookController
{
    @Autowired
    private RedisCacheLogic redisCacheLogic;

    // 精选书籍列表
    @GetMapping("boutique")
    public void indexBook(HttpServletResponse response,@RequestParam(defaultValue = "1") int page) throws IOException
    {
        ResponseUtil.json(response,redisCacheLogic.cacheAnGetIndexBookList(page));
    }
}
