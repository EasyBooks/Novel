/*
 * 作者：刘时明
 * 时间：2020/1/19-19:04
 * 作用：
 */
package com.novel.book.config;

import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;

@Configuration
@MapperScan("com.novel.book.mapper")
public class PlusConfig
{
    /**
     * 表名配置
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor()
    {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<>(4)
        {{
            put("type", (metaObject, sql, tableName) -> "t_" + tableName);
            put("book", (metaObject, sql, tableName) -> "t_" + tableName);
            put("chapter", (metaObject, sql, tableName) -> "t_" + tableName);
        }});
        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
        return paginationInterceptor;
    }
}
