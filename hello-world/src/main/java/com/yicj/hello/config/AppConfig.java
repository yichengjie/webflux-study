package com.yicj.hello.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@Configuration
public class AppConfig extends AsyncConfigurerSupport {

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
        //return new DelegatingSecurityContextExecutor(executor) ;
    }

    @Override
    public Executor getAsyncExecutor() {
        return asyncExecutor();
    }
}
