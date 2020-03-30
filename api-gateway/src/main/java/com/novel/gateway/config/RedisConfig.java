/*
 * 作者：刘时明
 * 时间：2020/3/29-10:40
 * 作用：
 */
package com.novel.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig
{
    @Bean(name = "redisTemplate")
    @Primary
    public RedisTemplate<String,Object> initRedisTemplate(@Autowired RedisConnectionFactory factory)
    {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>( );
        // 通过连接工厂获取连接
        redisTemplate.setConnectionFactory(factory);
        // json序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        return redisTemplate;
    }
}
