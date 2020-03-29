/*
 * 作者：刘时明
 * 时间：2020/3/29-21:22
 * 作用：
 */
package com.novel.reptile.facade.jinyong;

import com.novel.common.define.Define;
import com.novel.common.domain.BaseEntity;
import com.novel.common.domain.book.Book;
import com.novel.common.domain.book.Chapter;
import com.novel.common.utils.Snowflake;
import com.novel.reptile.facade.ReptileConfig;
import com.novel.reptile.facade.ReptileStart;
import com.novel.reptile.facade.ReptileType;
import com.novel.reptile.nsq.NsqProduce;
import com.novel.reptile.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 金庸网资源爬虫
 */
@Component
@Slf4j
public class JinyongReptileFacade implements ReptileStart
{
    private static final String BASE_URL = "http://www.jinyongwang.com/";

    @Autowired
    private Snowflake snowflake;
    @Autowired
    private NsqProduce produce;

    private Map<String, String> bookMap;
    private Map<String, Integer> startPageMap;
    private Set<Integer> pageSet;
    private String name;

    {
        bookMap = new HashMap<>();
        pageSet = new HashSet<>();
        startPageMap = new HashMap<>();
        startPageMap.put("天龙八部", 644);
        pageSet.add(644);
        startPageMap.put("鹿鼎记", 484);
        pageSet.add(484);
        startPageMap.put("飞狐外传", 822);
        pageSet.add(822);
        startPageMap.put("雪山飞狐", 770);
        pageSet.add(770);
        startPageMap.put("连城诀", 418);
        pageSet.add(418);
        startPageMap.put("射雕英雄传", 159);
        pageSet.add(159);
        startPageMap.put("白马啸西风", 432);
        pageSet.add(432);
        startPageMap.put("笑傲江湖", 696);
        pageSet.add(696);
        startPageMap.put("书剑恩仇录", 570);
        pageSet.add(570);
        startPageMap.put("侠客行", 748);
        pageSet.add(748);
        startPageMap.put("鸳鸯刀", 442);
        pageSet.add(442);
        startPageMap.put("倚天屠龙记", 443);
        pageSet.add(443);
        startPageMap.put("越女剑", 213);
        pageSet.add(213);
        startPageMap.put("碧血剑", 546);
        pageSet.add(213);
        startPageMap.put("神雕侠侣", 781);
        pageSet.add(781);
    }

    private long getChapterId(String name)
    {
        return startPageMap.get(name) + name.hashCode();
    }

    @Override
    public void start()
    {
        if (this.name == null)
        {
            // 全量爬
            allReptile();
        } else
        {
            // 定向爬
            appointReptile(this.name);
        }
    }

    @Override
    public ReptileType type()
    {
        return ReptileType.JINYONG;
    }

    @Override
    public void init(ReptileConfig config)
    {
        this.name = config.getName();
        Document document = JsoupUtil.getHtmlTextByUrl(BASE_URL);
        Element bookList = document.getElementById("book_ul");
        Elements li = bookList.select("li");
        for (Element e : li)
        {
            Elements elements = e.select("a");
            int count = 0;
            for (Element e1 : elements)
            {
                if (count % 4 == 1)
                {
                    String bookName = e1.text();
                    bookName = bookName.substring(0, bookName.length() - 2);
                    String url = BASE_URL + e1.attr("href").substring(1);
                    bookMap.put(bookName, url);
                }
                count++;
            }
        }
    }

    private void allReptile()
    {
        bookMap.forEach(this::reptile);
        System.out.println(bookMap.size());
        System.out.println(startPageMap.size());
    }

    private void appointReptile(String name)
    {
        String url = bookMap.get(name);
        if (url == null)
        {
            System.err.println("找不到小说：" + name);
        } else
        {
            reptile(name, url);
        }
    }

    private void reptile(String name, String url)
    {
        log.info("开始爬取小说={},url={}", name, url);
        if (!startPageMap.containsKey(name))
        {
            System.err.println("找不到小说：" + name);
            return;
        }
        Book book = getBook(name);
        produce.produce("book_reptile", book);
        log.info("book爬取完毕");
        List<Chapter> chapterList = getChapterList(name, url);
        int sort = 1;
        for (Chapter c : chapterList)
        {
            c.setBookId(book.getId());
            c.setSorted(sort++);
            produce.produce("chapter_reptile", c);
        }
    }

    private Book getBook(String name)
    {
        Book book = BaseEntity.initEntity(Book.class);
        book.setTypeId(1L);
        book.setId(snowflake.nextId());
        book.setTitle(name);
        book.setSynopsis("暂无简介");
        book.setCover("");
        book.setClick(0);
        book.setInstalments(0);
        book.setCollection(0);
        book.setRecommend(0);
        book.setWordNum(0L);
        book.setPlatformId(Define.JINYONG_PLATFORM);
        book.setThirdId((long) startPageMap.get(name));
        return book;
    }

    private List<Chapter> getChapterList(String name, String url)
    {
        List<Chapter> chapterList = new ArrayList<>();
        int seq = startPageMap.get(name);
        while (true)
        {
            Chapter chapter = getChapter(seq++, url);
            if (pageSet.contains(seq) || chapter == null) break;
            log.info("爬到一个章节={}", chapter.getName());
            chapterList.add(chapter);
        }
        return chapterList;
    }

    private Chapter getChapter(int seq, String url)
    {
        try
        {
            Chapter chapter = BaseEntity.initEntity(Chapter.class);
            chapter.setId(snowflake.nextId());
            chapter.setPlatformId(Define.JINYONG_PLATFORM);
            chapter.setThirdId((long) seq);
            Document document = JsoupUtil.getHtmlTextByUrl(url + seq + ".html");
            String title = document.getElementById("title").text();
            Elements select = document.getElementById("vcon").select("p");
            StringBuilder content = new StringBuilder();
            for (Element e : select)
            {
                content.append(e.text());
            }
            chapter.setName(title);
            chapter.setContent(content.toString());
            return chapter;
        } catch (Exception e)
        {
            return null;
        }
    }
}
