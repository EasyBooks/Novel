/*
 * 作者：刘时明
 * 时间：2020/3/29-14:42
 * 作用：
 */

import org.junit.Test;

public class Id
{
    String url = "http://book.zongheng.com/chapter/920255/59305460.html";

    @Test
    public void test()
    {
        int index = url.lastIndexOf("/");
        System.out.println(url.substring(index + 1, url.length() - 5));
    }
}
