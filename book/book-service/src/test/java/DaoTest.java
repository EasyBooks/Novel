/*
 * 作者：刘时明
 * 时间：2020/3/24-23:36
 * 作用：
 */

import com.novel.book.BookApplication;
import com.novel.book.mapper.CircleCommentMapper;
import com.novel.book.mapper.CircleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = BookApplication.class)
public class DaoTest
{
    @Autowired
    private CircleMapper circleMapper;
    @Autowired
    private CircleCommentMapper circleCommentMapper;

    @Transactional
    @Test
    public void test1()
    {
        System.out.println(circleCommentMapper.circleCommentLit(1L, 0, 10));
    }
}
