package com.yicj.hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-21 09:46
 **/
@Slf4j
@RestController
public class HelloController {

    // 普通的SpringMVC方法
    @GetMapping("/mvc")
    private String get1() {
        log.info("get1 start");
        String result = createStr();
        log.info("get1 end.");
        return result;
    }

    // WebFlux(返回的是Mono)
    @GetMapping("/flux")
    private Mono<String> get2() {
        log.info("get2 start");
        Mono<String> result = Mono.fromSupplier(() -> createStr());
        log.info("get2 end.");
        return result;
    }

    /**
     * Flux : 返回0-n个元素
     * 注：需要指定MediaType
     * @return
     */
    @GetMapping(value = "/flux2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> flux() {
        log.info("flux2 ....");
        return Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                String data = "flux data--" + i ;
                log.info("data : {}", data);
                return data;
            })).subscribeOn(Schedulers.parallel());
    }

    // 阻塞5秒钟
    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
        }
        log.info("===> create str ....");
        return "some string";
    }
}
