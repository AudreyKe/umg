package com.weshare.umg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/16 14:18
 * @Description:
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${thread.pool.corePoolSize}")
    private Integer corePoolSize;
    @Value("${thread.pool.maxPoolSize}")
    private Integer maxPoolSize;
    @Value("${thread.pool.keepAliveSeconds}")
    private Integer keepAliveSeconds;
    @Value("${thread.pool.queueCapacity}")
    private Integer queueCapacity;



    @Bean()
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("-umg-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
