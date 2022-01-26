package com.yicj.future.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 17:05
 **/
@Slf4j
public class CompletableFutureSerialExecutionTest {
    /**
     * CompletableFuture实现串行操作，
     * 第一个CompletableFuture根据证券名称查询证券代码，
     * 第二个CompletableFuture根据证券代码查询证券价格
     */
    @Test
    public void serialExecution() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        StopWatch watch = new StopWatch() ;
        watch.start("开始执行");
        String name = "中国石油" ;
        CompletableFuture<Void> future = CompletableFuture
                // 根据名称查询编码
                .supplyAsync(() -> queryCode(name))
                // 根据编码查询价格
                .thenApplyAsync(CompletableFutureSerialExecutionTest::getPrice)
                // 执行成功回调
                .thenAcceptAsync(result -> {
                    log.info("result : {}", result);
                }, pool);
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
        future.join() ;
        watch.stop();
        log.info("执行耗时 : {}", watch.getTotalTimeSeconds());
    }

    public static String queryCode(String name){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }
        String result = "2022" ;
        log.info("queryCode return : {}", result);
        return  result;
    }

    public static Double getPrice(String code){
        try {
            TimeUnit.MILLISECONDS.sleep(100) ;
        }catch (InterruptedException e){
            // ignore
        }
        Double result = Math.random() * 100 ;
        log.info("getPrice input code : {} , return : {}", code, result);
        return  result;
    }


}
