/*
 * 作者：刘时明
 * 时间：2020/3/29-14:42
 * 作用：
 */

import com.novel.reptile.facade.ReptileConfig;
import com.novel.reptile.facade.jinyong.JinyongReptileFacade;
import org.junit.Test;


public class Id
{
    @Test
    public void test()
    {
        JinyongReptileFacade facade = new JinyongReptileFacade();
        ReptileConfig config = new ReptileConfig();
        config.setName("倚天屠龙记");
        facade.init(config);
        facade.start();
    }
}
