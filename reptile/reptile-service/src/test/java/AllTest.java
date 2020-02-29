/*
 * 作者：刘时明
 * 时间：2020/2/28-14:23
 * 作用：
 */

import com.novel.common.domain.BaseEntity;
import com.novel.common.domain.book.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;
import java.util.Random;

public class AllTest
{
    private static final int TIME_OUT = 5 * 1000;
    private static final String END_HREF = "访问页面不存在";
    private static final Random RANDOM = new Random();

    @Test
    public void test()
    {
        String url = String.format("http://book.zongheng.com/book/%s.html", 123438);
        Document document = getHtmlTextByUrl(url);
        Book info = getBookInfoByUrl(document);
        System.out.println(info);
    }

    private Book getBookInfoByUrl(Document document)
    {
        Book book= BaseEntity.initEntity(Book.class);
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

    private void work(int start)
    {
        int i = start;
        try
        {
            PrintWriter writer = new PrintWriter(new FileOutputStream("1.txt", true));
            for (i = start; i < 999999; i++)
            {
                String url = String.format("http://book.zongheng.com/book/%s.html", i);
                Document document = getHtmlTextByUrl(url);
                if (!document.select("title").first().text().equals(END_HREF))
                {
                    writer.write(i + "\n");
                    writer.flush();
                    System.out.println(i + " ->");
                } else
                {
                    System.out.println(i);
                }
            }
        } catch (NullPointerException | FileNotFoundException e1)
        {
            System.err.println("发生异常");
            work(i);
        }
    }

    private Document getHtmlTextByUrl(String url)
    {
        Document document = null;
        try
        {
            // 防止被拉黑，随机延迟
            Thread.sleep(RANDOM.nextInt(100));
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
