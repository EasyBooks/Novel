package com.novel.gateway.logic.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 18:43
 **/
@Component
public class RedisCacheLogic
{
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static String categoryJson="{\"male\":[{\"name\":\"玄幻\",\"bookCount\":184308},{\"name\":\"奇幻\",\"bookCount\":26036},{\"name\":\"武侠\",\"bookCount\":16421},{\"name\":\"仙侠\",\"bookCount\":53187},{\"name\":\"都市\",\"bookCount\":105904},{\"name\":\"职场\",\"bookCount\":4870},{\"name\":\"历史\",\"bookCount\":23904},{\"name\":\"军事\",\"bookCount\":6073},{\"name\":\"游戏\",\"bookCount\":24769},{\"name\":\"竞技\",\"bookCount\":2686},{\"name\":\"科幻\",\"bookCount\":37337},{\"name\":\"灵异\",\"bookCount\":28158},{\"name\":\"同人\",\"bookCount\":15439},{\"name\":\"轻小说\",\"bookCount\":5850}],\"female\":[{\"name\":\"古代言情\",\"bookCount\":194578},{\"name\":\"现代言情\",\"bookCount\":228781},{\"name\":\"青春校园\",\"bookCount\":64469},{\"name\":\"纯爱\",\"bookCount\":1366},{\"name\":\"玄幻奇幻\",\"bookCount\":47317},{\"name\":\"武侠仙侠\",\"bookCount\":24926},{\"name\":\"科幻\",\"bookCount\":11685},{\"name\":\"游戏竞技\",\"bookCount\":567},{\"name\":\"悬疑灵异\",\"bookCount\":5344},{\"name\":\"同人\",\"bookCount\":13330},{\"name\":\"女尊\",\"bookCount\":8716},{\"name\":\"莉莉\",\"bookCount\":591}],\"picture\":[{\"name\":\"热血\",\"bookCount\":1020},{\"name\":\"魔幻\",\"bookCount\":423},{\"name\":\"科幻\",\"bookCount\":25},{\"name\":\"恋爱\",\"bookCount\":1615},{\"name\":\"搞笑\",\"bookCount\":1411},{\"name\":\"悬疑\",\"bookCount\":511},{\"name\":\"少儿\",\"bookCount\":4558}],\"press\":[{\"name\":\"传记名著\",\"bookCount\":8484},{\"name\":\"出版小说\",\"bookCount\":20759},{\"name\":\"人文社科\",\"bookCount\":100452},{\"name\":\"生活时尚\",\"bookCount\":3896},{\"name\":\"经管理财\",\"bookCount\":7610},{\"name\":\"青春言情\",\"bookCount\":20182},{\"name\":\"外文原版\",\"bookCount\":2591},{\"name\":\"政治军事\",\"bookCount\":798},{\"name\":\"成功励志\",\"bookCount\":18215},{\"name\":\"育儿健康\",\"bookCount\":16158}],\"ok\":true}";
    private static String bannerJson="{\"code\":200,\"result\":[{\"id\":1,\"title\":\"花语\",\"link\":\"http://news.zongheng.com/subject/422.html?zong_heng_share_bool=1\",\"img\":\"//static.zongheng.com/upload/recommend/59/1c/591ce0df7cadb330af33833dec2eeca3.jpeg\"},{\"id\":2,\"title\":\"帝道独尊\",\"link\":\"http://book.zongheng.com/book/735577.html\",\"img\":\"//static.zongheng.com/upload/recommend/5c/31/5c316a48397e574cb4422f353898f9a9.jpeg\"},{\"id\":3,\"title\":\"这么多小说网站里，只有纵横看书爽\",\"link\":\"\",\"img\":\"//static.zongheng.com/upload/recommend/cc/91/cc917016d06ffe4125cd40094898b389.jpeg\"},{\"id\":4,\"title\":\"万界仙王\",\"link\":\"http://book.zongheng.com/book/711996.html\",\"img\":\"//static.zongheng.com/upload/recommend/ac/87/ac874aeb7845d6ac9dbe243c5da9a191.jpeg\"}],\"message\":\"ok\"}";

    public String cacheAnGetBannerList()
    {
        String key="banner";
        String banner = redisTemplate.opsForValue().get(key);
        if(banner==null)
        {
            redisTemplate.opsForValue().set(key,bannerJson,30 , TimeUnit.MINUTES);
            return bannerJson;
        }
        return banner;
    }

    public String cacheAnGetCategoryList()
    {
        String key= "category";
        String category = redisTemplate.opsForValue().get(key);
        if(category==null)
        {
            redisTemplate.opsForValue().set(key,categoryJson,30 , TimeUnit.MINUTES);
            return categoryJson;
        }
        return category;
    }
}
