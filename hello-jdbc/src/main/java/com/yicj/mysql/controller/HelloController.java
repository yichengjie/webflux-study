package com.yicj.mysql.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-17 10:08
 **/
@Slf4j
@RestController
public class HelloController {

    @GetMapping("sync")
    public Future<String> sync()  {
        log.info("sync method start");
        CompletableFuture<String> execute = this.execute();
        log.info("sync method end");
        return execute;
    }

    @GetMapping("async/mono")
    public Mono<String> asyncMono() {
        log.info("async method start");
        Mono<String> result = Mono.fromFuture(this::execute);
        log.info("async method end");
        return result;
    }

    private CompletableFuture<String> execute() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("biz execute start....");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("biz execute end....");
            return "hello" ;
        });
        return future;
    }
}
