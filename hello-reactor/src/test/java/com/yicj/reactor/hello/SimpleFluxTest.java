package com.yicj.reactor.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class SimpleFluxTest {

    @Test
    public void test01(){
        Flux<Integer> flux1 = Flux.just(1);
        Flux<Integer> flux2 = Flux.just(2);
        Flux<Integer> flux3 = Flux.just(3);
        Flux.zip(flux1, flux2, flux3).subscribe((v1) -> {
            log.info("value : {}", v1);
        }) ;
    }


    @Test
    public void rollback() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> cf = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("异常报错");
        })/*.exceptionally(throwable -> {
            log.info("error: {}", throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        })*/;
        // 回滚测试
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            log.info("启动A");
            try {
                Thread.sleep(1000);
                // do busi
            } catch (InterruptedException e) {
                // 异常处理
                e.printStackTrace();
            }
            return "completableFutureA";
        }).exceptionally(throwable -> {
            log.error("error : {}", throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }).thenCombine(cf, (s1, s2) -> s1 + " , " + s2);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            log.info("启动B");
            try {
                Thread.sleep(100);
                // do busi
            } catch (InterruptedException e) {
                // 异常处理
                e.printStackTrace();
            }
            return "completableFutureB";
        });

        CompletableFuture<String> futureC = futureA.thenApplyAsync(c -> {
            log.info("启动C");
            try {
                // do busi
            } catch (Exception e) {
                // 异常处理
                e.printStackTrace();
            }
            return "completableFutureC";
        });
        //
        CompletableFuture<Void> future = CompletableFuture.allOf(futureA, futureB, futureC);
        log.info("resultA: {}, resultB: {}, resultC: {}", futureA.get(), futureB.get(), futureC.get());
        log.info("result : {}", future.get());
    }
}
