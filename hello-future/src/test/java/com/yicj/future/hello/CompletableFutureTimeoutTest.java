package com.yicj.future.hello;

import com.yicj.future.util.CompletableFutureTimeout;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 15:53
 **/
@Slf4j
public class CompletableFutureTimeoutTest {

    @Test
    public void timeoutIntegral() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        //
        CompletableFuture<Integer> within = CompletableFutureTimeout.completeOnTimeout(1, cf1, 1, TimeUnit.SECONDS);
        log.info("within result : {}", within.get());
    }

    @Test
    public void timeoutString() throws ExecutionException, InterruptedException {
        StopWatch watch = new StopWatch() ;
        watch.start("test");
        //
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "正常执行";
        });
        CompletableFuture<String> within = CompletableFutureTimeout.completeOnTimeout("异常执行", future, 1, TimeUnit.SECONDS);
        log.info("within result : {}", within.get());
        watch.stop();
        log.info("耗时 : {}", watch.getTotalTimeSeconds());
    }

    @Test
    public void normalString() throws ExecutionException, InterruptedException {
        //
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "正常执行";
        });
        CompletableFuture<String> within = CompletableFutureTimeout.completeOnTimeout("异常执行", future, 1, TimeUnit.SECONDS);
        log.info("within result : {}", within.get());
    }
}
