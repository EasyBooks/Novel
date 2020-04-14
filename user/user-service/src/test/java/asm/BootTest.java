/*
 * 作者：刘时明
 * 时间：2020/4/14-22:31
 * 作用：
 */
package asm;

import com.novel.user.UserApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserApplication.class)
public class BootTest
{
    @Test
    public void test1()
    {
        System.out.println("hello");
    }
}
