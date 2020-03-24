/*
 * 作者：刘时明
 * 时间：2020/3/24-23:36
 * 作用：
 */

import com.novel.book.BookApplication;
import com.novel.book.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = BookApplication.class)
public class DaoTest
{
    @Autowired
    private BookMapper bookMapper;

    @Test
    public void test1()
    {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("page", 0);
        conditionMap.put("size", 10);
        // conditionMap.put("title", "道");
        System.out.println(bookMapper.queryBookDto(conditionMap));
    }
}
