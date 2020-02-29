import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.*;

public class Test
{
    private static final int TIME_OUT=5*1000;
    private static final String END_HREF="javascript:void(0)";
    private static final Random RANDOM=new Random();

    @org.junit.Test
    public void url()
    {
        String url = String.format("http://book.zongheng.com/book/%s.html", 123438);
        Document document = getHtmlTextByUrl(url);
        document.select("a").forEach(e ->
        {
            if(e.text().equals("开始阅读"))
            {
                getBookChapter(e.attr("href"));
            }
        });
    }

    @org.junit.Test
    public void test()
    {
        Map<String, String> chapterMap = search("苦涯", 2);
        chapterMap.forEach((k,v)->
        {

        });
    }

    /**
     * 搜索小说，返回第一章URL和书名
     * @param keyword
     * @param size
     * @return
     */
    private Map<String,String> search(String keyword, int size)
    {
        String url=String.format("http://search.zongheng.com/s?keyword=%s",keyword);
        Document document=getHtmlTextByUrl(url);
        Elements elements = document.body().getElementsByClass("fl se-result-infos");
        if(size> elements.size())
        {
            size=elements.size();
        }
        Map<String,String> result=new LinkedHashMap<>(size);
        int count=0;
        for (Element e:elements)
        {
            if(++count>size)
            {
                break;
            }
            Elements tit = e.getElementsByClass("tit");
            if(tit.size()==1)
            {
                // key=主页URL，value=书名
                String bookUrl=tit.get(0).child(0).attr("href");
                result.put(getChapterUrl(bookUrl),tit.get(0).child(0).text());
            }
        }
        return result;
    }

    /**
     * 根据书籍的主页URL获取第一章的URL
     * @param bookUrl
     * @return
     */
    private String getChapterUrl(String bookUrl)
    {
        Document document = getHtmlTextByUrl(bookUrl);
        Elements elements = document.getElementsByClass("btn-group");
        if(elements.size()==1)
        {
            for (Node node:elements.get(0).childNodes())
            {
                if(node.nodeName().equals("a"))
                {
                    return node.attr("href");
                }
            }
        }
        return null;
    }

    /**
     * 根据第一章URL爬取小说标题和内容
     * @param url
     */
    private void getBookChapter(String url)
    {
        do
        {
            Document document = getHtmlTextByUrl(url);
            url=getNextUrl(document);
            // 标题
            System.out.println(getTitle(document));
            // 内容
            System.out.println(getText(document));
        }while (!url.equals(END_HREF));
    }

    /**
     * 获取下一章的URL
     * @param document
     * @return
     */
    private String getNextUrl(Document document)
    {
        Elements content = document.body().getElementsByClass("chap_btnbox");
        if(content.size()==1)
        {
            Element element = content.get(0);
            int count=0;
            for (Node node:element.childNodes())
            {
                String href=node.attr("href");
                if(href.trim().equals(""))
                {
                    continue;
                }else
                {
                    // 下一章的链接
                    if(++count==3)
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
     * @param document
     * @return
     */
    private String getTitle(Document document)
    {
        Elements content = document.body().getElementsByClass("title");
        if(content.size()==1)
        {
            return content.get(0).text();
        }else
        {
            return null;
        }
    }

    /**
     * 获取章节内容
     * @param document
     * @return
     */
    private String getText(Document document)
    {
        Elements content = document.body().getElementsByClass("content");
        if(content.size()==1)
        {
            return content.get(0).text();
        }else
        {
            return null;
        }
    }

    /**
     * 根据url获取数据
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
}
