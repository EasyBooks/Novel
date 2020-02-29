import com.github.brainlag.nsq.exceptions.NSQException;
import com.novel.user.nsq.NsqProduce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeoutException;

@SpringBootTest
class UserApplicationTests
{
    @Autowired
    private NsqProduce nsqProduce;

    @Test
    public void nsqProduce()
    {

    }
}

