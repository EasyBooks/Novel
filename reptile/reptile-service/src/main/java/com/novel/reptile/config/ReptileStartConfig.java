package com.novel.reptile.config;

import com.novel.reptile.facade.ReptileBuild;
import com.novel.reptile.facade.ReptileStart;
import com.novel.reptile.facade.ReptileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReptileStartConfig implements ApplicationRunner
{
    @Autowired
    private ReptileBuild reptileBuild;

    @Override
    public void run(ApplicationArguments args)
    {
        /**
         * 指定爬虫资源类型和配置
         */
        ReptileStart reptileStart = reptileBuild
                .type(ReptileType.JINYONG)
                .name("天龙八部")
                .start(127000)
                .end(127200)
                .build();
        reptileStart.start();
    }
}
