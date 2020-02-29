/*
 * 作者：刘时明
 * 时间：2019/11/23-22:19
 * 作用：公共配置
 */
package com.novel.book.config;

import com.novel.common.utils.Snowflake;
import org.apache.dubbo.config.MetadataReportConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class CommonConfig
{
    /**
     * 线程池配置
     *
     * @return
     */
    @Bean
    public Executor asyncServiceExecutor(@Value("${spring.application.name}") String serviceName)
    {
        // 线程池配置固定，直接写死
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(10);
        // 配置最大线程数
        executor.setMaxPoolSize(20);
        // 配置队列大小
        executor.setQueueCapacity(500);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(serviceName + "-");
        // 如果线程池任务已满，则由调用者自己处理
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor;
    }

    /**
     * dubbo元数据配置
     *
     * @return
     */
    @Bean
    public MetadataReportConfig metadataReportConfig(@Value("${nacos.service-address}") String address,
                                                     @Value("${nacos.port}") int port)
    {
        MetadataReportConfig config = new MetadataReportConfig();
        config.setAddress(String.format("nacos://%s:%d", address, port));
        return config;
    }

    /**
     * 雪花算法机器配置
     *
     * @return
     */
    @Bean
    public Snowflake snowflake(@Value("${snowflake.machine-id}") long machineId,
                               @Value("${snowflake.app-id}") long appId)
    {
        return new Snowflake(machineId, appId);
    }
}
