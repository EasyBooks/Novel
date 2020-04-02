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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = BookApplication.class)
public class DaoTest
{
    @Autowired
    private BookMapper bookMapper;

    @Reference(version = "1.0.0",check = false)
    private RPCUserService userService;

    @Transactional
    @Test
    public void test1()
    {
        List<Book> books = bookMapper.selectList(null);
        for (Book b : books)
        {
            if(b.getId()<=561555830801969152L){
                userService.saveAuthor(1L, b.getId());
            }else if(b.getId()>=561936135706652672L)
            {
                userService.saveAuthor(1L, b.getId());
            }else
            {
                userService.saveAuthor(3L, b.getId());
            }
        }
    }
}
