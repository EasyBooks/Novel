package com.novel.reptile.facade;

import com.novel.common.domain.BaseEntity;
import com.novel.common.domain.book.Book;
import com.novel.common.domain.book.Chapter;
import com.novel.common.utils.Snowflake;
import com.novel.reptile.nsq.NsqProduce;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReptileBookFacade
{
    private static final int TIME_OUT = 5 * 1000;
    private static final String END_HREF = "javascript:void(0)";
    private static final Random RANDOM = new Random();
    private static final String NOT_FIND_STR = "访问页面不存在";
    @Autowired
    private Snowflake snowflake;
    @Autowired
    private NsqProduce produce;

    @Async
    public void allReptile()
    {
        // work(100000);
        work(123438);
    }

    private void work(int start)
    {
        try
        {
            //for (; start < 999999; start++)
            {
                String url = String.format("http://book.zongheng.com/book/%s.html", start);
                Document document = getHtmlTextByUrl(url);
                if (!document.select("title").first().text().equals(NOT_FIND_STR))
                {
                    Book book = getBookInfoByUrl(document);
                    System.out.println(book);
                    List<Chapter> chapterList = getChapterListByUrl(url);
                    int sort=1;
                    for (Chapter c:chapterList)
                    {
                        c.setId(snowflake.nextId());
                        c.setBookId(book.getId());
                        c.setSorted(sort++);
                    }
                }
            }
        } catch (NullPointerException e)
        {
            work(start);
        }
    }

    private Book getBookInfoByUrl(Document document)
    {
        Book book= BaseEntity.initEntity(Book.class);
        book.setId(snowflake.nextId());
        book.setTypeId(1L);
        book.setClick(0);
        book.setInstalments(0);
        book.setCollection(0);
        book.setRecommend(0);
        book.setWordNum(0L);
        document.select("div").forEach(e->
                {
                    String cla=e.attr("class");
                    if(cla.equals("book-name"))
                    {
                        book.setTitle(e.text());
                    }else if("book-img fl".equals(cla))
                    {
                        book.setCover(e.child(1).attr("src"));
                    }else if(cla.equals("book-dec Jbook-dec hide"))
                    {
                        book.setSynopsis(e.child(0).text());
                    }
                }
        );
        return book;
    }

    private List<Chapter> getChapterListByUrl(String url)
    {
        Document document = getHtmlTextByUrl(url);
        for(Element d:document.select("a"))
        {
            if(d.text().equals("开始阅读"))
            {
                return getBookChapter(d.attr("href"));
            }
        }
        return null;
    }

    /**
     * 搜索小说，返回第一章URL和书名
     *
     * @param keyword
     * @param size
     * @return
     */
    public Map<String, String> search(String keyword, int size)
    {
        String url = String.format("http://search.zongheng.com/s?keyword=%s", keyword);
        Document document = getHtmlTextByUrl(url);
        Elements elements = document.body().getElementsByClass("fl se-result-infos");
        if (size > elements.size())
        {
            size = elements.size();
        }
        Map<String, String> result = new LinkedHashMap<>(size);
        int count = 0;
        for (Element e : elements)
        {
            if (++count > size)
            {
                break;
            }
            Elements tit = e.getElementsByClass("tit");
            if (tit.size() == 1)
            {
                // key=主页URL，value=书名
                String bookUrl = tit.get(0).child(0).attr("href");
                result.put(getChapterUrl(bookUrl), tit.get(0).child(0).text());
            }
        }
        return result;
    }

    /**
     * 根据书籍的主页URL获取第一章的URL
     *
     * @param bookUrl
     * @return
     */
    private String getChapterUrl(String bookUrl)
    {
        Document document = getHtmlTextByUrl(bookUrl);
        Elements elements = document.getElementsByClass("btn-group");
        if (elements.size() == 1)
        {
            for (Node node : elements.get(0).childNodes())
            {
                if (node.nodeName().equals("a"))
                {
                    return node.attr("href");
                }
            }
        }
        return null;
    }

    /**
     * 根据第一章URL爬取小说标题和内容
     *
     * @param url
     */
    public List<Chapter> getBookChapter(String url)
    {
        List<Chapter> chapterList = new ArrayList<>();
        do
        {
            Chapter chapter = BaseEntity.initEntity(Chapter.class);
            Document document = getHtmlTextByUrl(url);
            url = getNextUrl(document);
            // 标题
            chapter.setName(getTitle(document));
            // 内容
            chapter.setContent(getText(document));
            chapterList.add(chapter);
        } while (!url.equals(END_HREF));
        return chapterList;
    }

    /**
     * 获取下一章的URL
     *
     * @param document
     * @return
     */
    private String getNextUrl(Document document)
    {
        Elements content = document.body().getElementsByClass("chap_btnbox");
        if (content.size() == 1)
        {
            Element element = content.get(0);
            int count = 0;
            for (Node node : element.childNodes())
            {
                String href = node.attr("href");
                if (href.trim().equals(""))
                {
                    continue;
                } else
                {
                    // 下一章的链接
                    if (++count == 3)
                    {
                        return href;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取章节标题
     *
     * @param document
     * @return
     */
    private String getTitle(Document document)
    {
        Elements content = document.body().getElementsByClass("title");
        if (content.size() == 1)
        {
            return content.get(0).text();
        } else
        {
            return null;
        }
    }

    /**
     * 获取章节内容
     *
     * @param document
     * @return
     */
    private String getText(Document document)
    {
        Elements content = document.body().getElementsByClass("content");
        if (content.size() == 1)
        {
            return content.get(0).text();
        } else
        {
            return null;
        }
    }

    /**
     * 根据url获取数据
     *
     * @param url
     * @return
     */
    private Document getHtmlTextByUrl(String url)
    {
        Document document = null;
        try
        {
            // 防止被拉黑，随机延迟
            Thread.sleep(RANDOM.nextInt(1000));
            document = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(TIME_OUT).post();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return document;
    }

    public Book getBookByUrl(String url)
    {

        Book book = BaseEntity.initEntity(Book.class);

        return book;
    }
}
