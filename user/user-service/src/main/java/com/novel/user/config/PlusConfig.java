/*
 * 作者：刘时明
 * 时间：2020/1/19-19:04
 * 作用：
 */
package com.novel.user.config;

import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;

@Configuration
@MapperScan("com.novel.user.mapper")
public class PlusConfig
{
    /**
     * 表名配置
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor()
    {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<>(2)
        {{
            put("user_info", (metaObject, sql, tableName) -> "t_user_info");
            put("user_details", (metaObject, sql, tableName) -> "t_user_details");
            put("collection", (metaObject, sql, tableName) -> "t_" + tableName);
            put("follow", (metaObject, sql, tableName) -> "t_" + tableName);
            put("circle", (metaObject, sql, tableName) -> "t_" + tableName);
            put("circle_comment", (metaObject, sql, tableName) -> "t_circle_comment");
            put("circle_like", (metaObject, sql, tableName) -> "t_circle_like");
        }});
        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
        return paginationInterceptor;
    }
}
