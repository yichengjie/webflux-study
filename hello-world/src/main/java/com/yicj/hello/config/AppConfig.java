package com.yicj.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AppConfig {

    @Bean("asyncExecutor")
    public Executor asyncExecutor(){
        // 默认异步执行器
        //return new SimpleAsyncTaskExecutor() ;
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() ;
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncMvc-");
        executor.initialize();
        return executor ;
    }

}
