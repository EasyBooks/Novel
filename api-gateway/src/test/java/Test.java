/*
 * 作者：刘时明
 * 时间：2020/3/29-0:56
 * 作用：
 */

import com.novel.common.domain.user.User;
import com.novel.gateway.GatewayApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
public class Test
{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @org.junit.Test
    public void test1()
    {
        redisTemplate.opsForHash().put(String.format("type-%d", 1234546566776L), "name", "魔幻");
        String key = String.format("type-%d", 1234546566776L);
        System.out.println(redisTemplate.opsForHash().get(key, "name"));
    }

    @org.junit.Test
    public void test2()
    {
        User user=new User();
        user.setNickname("hello");
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("mybe",user,5, TimeUnit.SECONDS);
        System.out.println(opsForValue.get("mybe"));
    }

    @org.junit.Test
    public void test3()
    {

    }
}
