/*
 * 作者：刘时明
 * 时间：2020/4/1-0:06
 * 作用：
 */

import com.novel.im.ImApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = ImApplication.class)
public class RedisTest
{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test1()
    {

    }
}
