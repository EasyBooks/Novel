package com.novel.gateway.handler.app;

import com.novel.common.dto.book.ChapterDto;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.aspect.annotation.IdParam;
import com.novel.gateway.logic.cache.RedisCacheLogic;
import com.novel.gateway.utils.ResponseUtil;
import com.novel.user.service.RPCBookService;
import com.novel.user.service.RPCChapterService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
    @Reference(version = "99.0.0",check = false)
    private RPCChapterService chapterService;
    @Reference(version = "99.0.0", check = false)
    private RPCBookService bookService;

    // 精选书籍列表
    @GetMapping("boutique")
    public void indexBook(HttpServletResponse response) throws IOException
    {
        ResponseUtil.json(response,redisCacheLogic.cacheAnGetBoutique());
    }

    // 小说详情
    @GetMapping("detail/{id}")
    public void detail(HttpServletResponse response,@PathVariable String id) throws IOException
    {
        ResponseUtil.json(response,redisCacheLogic.cacheAnGetDetail(Long.valueOf(id)));
    }

    // 阅读
    @GetMapping("read/{id}")
    public Object read(@PathVariable String id,@IdParam Integer uid)
    {
        ChapterDto read = chapterService.read(Long.parseLong(id),uid);
        return ResultUtil.success(read);
    }

    // 章节列表
    @GetMapping("chapters/{id}")
    public void chapters(HttpServletResponse response,@PathVariable String id) throws IOException
    {
        ResponseUtil.json(response,redisCacheLogic.cacheAnGetChapters(Long.valueOf(id)));
    }

    // 分类下的书籍列表
    @GetMapping("typeList/{id}")
    public Object typeList(@PathVariable String id,@RequestParam int page,@RequestParam int size)
    {
        Map<String,Object> conditionMap=Map.of("typeId", Long.parseLong(id));
        return ResultUtil.success(bookService.bookList(conditionMap,(page-1)*size,size));
    }
}
