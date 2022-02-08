package com.yicj.reactor.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-08 09:58
 **/
@Slf4j
public class HelloTest {

    @Test
    public void subscribeOn(){
        Flux<Integer> fluxMap = Flux.range(1, 4)
                .map(integer -> {
                    log.info("map1 number: {}", integer);
                    return integer;
                })
                .subscribeOn(Schedulers.single())
                .map(integer -> {
                    log.info("Map2 number: {}", integer);
                    return integer;
                });
        StepVerifier.create(fluxMap)
                .expectNext(1,2,3,4)
                .verifyComplete() ;
    }

    @Test
    public void publishOn(){
        /*Flux<Integer> fluxMap = */Flux.range(1, 4)
                .map(integer -> {
                    log.info("Map1 number : {}", integer);
                    return integer;
                })
                .publishOn(Schedulers.single())
                .map(integer -> {
                    log.info("Map2 number: {}", integer);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return integer;
                }).blockLast();
//        StepVerifier.create(fluxMap)
//                .expectNext(1,2,3,4)
//                .verifyComplete() ;
//        fluxMap.subscribe(value ->{
//
//        });

    }

    //// 执行时间大概8秒，创建新线程
    @Test
    public void newSingle(){
        log.info("start time : {}", LocalDateTime.now());
        Flux.just("tom","jack","allen")
                .log()
                .publishOn(Schedulers.newSingle("1"))
                .map(this::doSomething)
                .publishOn(Schedulers.newSingle("2"))
                .map(this::doSomething)
                .publishOn(Schedulers.newSingle("3"))
                .map(this::doSomething)
                .publishOn(Schedulers.newSingle("4"))
                .map(this::doSomething)
                .publishOn(Schedulers.newSingle("5"))
                .map(this::doSomething)
                .subscribeOn(Schedulers.newSingle("0"))
                .blockLast() ;
        log.info("end time : {}", LocalDateTime.now());
    }

    //// 执行时间15秒，使用相同线程
    @Test
    public void single(){
        log.info("start time : {}", LocalDateTime.now());
        Flux.just("tom","jack","allen")
                .log()
                .publishOn(Schedulers.single())
                .map(this::doSomething)
                .publishOn(Schedulers.single())
                .map(this::doSomething)
                .publishOn(Schedulers.single())
                .map(this::doSomething)
                .publishOn(Schedulers.single())
                .map(this::doSomething)
                .publishOn(Schedulers.single())
                .map(this::doSomething)
                .subscribeOn(Schedulers.single())
                .blockLast() ;
        log.info("end time : {}", LocalDateTime.now());
    }

    private String doSomething(String s){
        log.info("thread with value : {}",s);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s ;
    }

}
