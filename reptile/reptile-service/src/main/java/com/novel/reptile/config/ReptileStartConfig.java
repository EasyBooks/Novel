package com.novel.reptile.config;

import com.novel.reptile.facade.ReptileBookFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReptileStartConfig implements ApplicationRunner
{
    @Autowired
    private ReptileBookFacade bookFacade;

    @Override
    public void run(ApplicationArguments args)
    {
        bookFacade.allReptile();
    }
}
