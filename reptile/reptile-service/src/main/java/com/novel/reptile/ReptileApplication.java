package com.novel.reptile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ReptileApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ReptileApplication.class, args);
    }
}
