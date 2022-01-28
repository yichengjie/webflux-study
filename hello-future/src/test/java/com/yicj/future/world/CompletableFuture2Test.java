package com.yicj.future.world;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFuture2Test {

    // 创建异步任务，有返回值
    @Test
    public void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start time --> {}", System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // ignore
            }
            if (true) {
                throw new RuntimeException("test");
            } else {
                log.info("exit,time --> {}", System.currentTimeMillis());
                return 1.2;
            }
        });
        log.info("main thread start, time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("run result -> {}", cf.get());
        log.info("main thread exit, time: {}", System.currentTimeMillis());
    }

    // 创建异步执行任务，无返回值
    @Test
    public void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            log.info("start, time -> {}", System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // ignore
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info("exit, time -> {}", System.currentTimeMillis());
            }
        });
        log.info("main thread start, time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("run result -> {}", cf.get());
        log.info("main thread exit, time : {}", System.currentTimeMillis());
    }

    // 异步回调
    @Test
    public void thenApply() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool() ;
        // 创建异步执行任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // ignore
            }
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        }, pool);
        //cf关联得异步任务得返回值作为方法入参，传入到thenApply的方法中
        // thenApply这里实际创建了一个新的CompletableFuture实例
        CompletableFuture<String> cf2 = cf.thenApply(result -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // ignore
            }
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return "test: " + result;
        });
        //
        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("run result -> {}", cf.get());
        log.info("main thread start cf2.get(), time -> {}", System.currentTimeMillis());
        log.info("run result -> {}", cf2.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }
}
