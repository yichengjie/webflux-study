package com.yicj.future.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 16:55
 **/
@Slf4j
public class CompletableFutureResultTest {

    // 任务成功回调和异常回调
    @Test
    public void callback() throws InterruptedException {
        // 创建异步执行任务
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(CompletableFutureResultTest::computePrice)
                // 执行成功回调
                .thenAccept(result -> {
                    log.info("result : {}", result);
                })
                // 执行异常回调
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
        //主线程不要立刻结束，否则CompletableFuture默认使用的线程池也会立刻关闭
        TimeUnit.SECONDS.sleep(2);
    }

    public static Double computePrice(){
        try {
            TimeUnit.SECONDS.sleep(1);
            double value = Math.random();
            log.info("random value : {}", value);
            if (value < 0.3){
                throw new RuntimeException("too little !") ;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 5 + Math.random() *20 ;
    }
}
