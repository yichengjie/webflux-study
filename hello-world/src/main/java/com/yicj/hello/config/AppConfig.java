package com.yicj.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AppConfig {

    @Bean("asyncExecutor")
    public Executor asyncExecutor(){
        // 默认异步执行器
        return new SimpleAsyncTaskExecutor() ;
    }

}
