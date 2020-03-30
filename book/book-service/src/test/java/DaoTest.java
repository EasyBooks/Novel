/*
 * 作者：刘时明
 * 时间：2020/3/24-23:36
 * 作用：
 */

import com.novel.book.BookApplication;
import com.novel.book.mapper.BookMapper;
import com.novel.common.domain.book.Book;
import com.novel.user.service.RPCUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = BookApplication.class)
public class DaoTest
{
    @Autowired
    private BookMapper bookMapper;

    @Reference(version = "1.0.0")
    private RPCUserService userService;

    @Test
    public void test1()
    {
        long userId = 551264367245656064L;
        List<Book> books = bookMapper.selectList(null);
        for (Book b : books)
        {
            userService.saveAuthor(userId, b.getId());
        }
    }
}
