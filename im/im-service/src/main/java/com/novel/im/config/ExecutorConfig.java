/*
 * 作者：刘时明
 * 时间：2020/3/21-9:58
 * 作用：
 */
package com.novel.im.config;

import com.novel.common.define.ResultTask;
import com.novel.common.define.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig
{
    private static ThreadPoolTaskExecutor executor;

    @Autowired
    public void setExecutor(ThreadPoolTaskExecutor executor)
    {
        ExecutorConfig.executor = executor;
    }

    public static void executor(Task task, Object args)
    {
        executor.execute(() -> task.taskWork((args)));
    }

    public static <E> E executor(ResultTask<E> task, Object args) throws ExecutionException, InterruptedException
    {
        return executor.submit(() -> task.taskWork(args)).get();
    }

    /**
     * 线程池配置
     *
     * @return
     */
    @Bean("taskExecutor")
    @Primary
    public ThreadPoolTaskExecutor asyncServiceExecutor(@Value("${spring.application.name}") String serviceName)
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
}
