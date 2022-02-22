package com.yicj.reactor.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Comparator;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-21 16:45
 **/
@Slf4j
public class ConcatAndMergeTest {

    @Test
    public void concat() throws InterruptedException {
        Flux.concat(hotFlux1(), hotFlux2())
                .subscribe(value -> log.info("value : {}", value));
        Thread.sleep(200);
    }

    @Test
    public void concatWith() throws InterruptedException {
        hotFlux1().concatWith(hotFlux2())
                .subscribe(value -> log.info("value : {}", value));
        Thread.sleep(200);
    }

    @Test
    public void merge() throws InterruptedException {
        Flux.merge(hotFlux1(), hotFlux2())
                .subscribe(value -> log.info("value : {}", value));
        Thread.sleep(200);
    }

    @Test
    public void mergeWith() throws InterruptedException {
        hotFlux1().mergeWith(hotFlux2())
                .subscribe(value -> log.info("value : {}", value));
        Thread.sleep(200);
    }

    @Test
    public void mergeSequential() throws InterruptedException {
        Flux.mergeSequential(hotFlux1(), hotFlux2())
                .subscribe(value -> log.info("value : {}", value));
        Thread.sleep(200);
    }

    @Test
    public void mergeOrdered() throws InterruptedException {
        Flux.mergeOrdered(Comparator.reverseOrder(), hotFlux1(), hotFlux2())
                .subscribe(value -> log.info("value : {}", value));
        Thread.sleep(200);
    }

    @Test
    public void combineLatestTest() throws InterruptedException {
        Flux.combineLatest(hotFlux1(), hotFlux2(), (v1, v2) -> v1 + ":" + v2)
                .subscribe(value -> log.info("{}", value));
        Thread.sleep(500);
    }

    @Test
    public void combineLatest2() throws InterruptedException {
        Flux.combineLatest(
                // 延迟50 ，每100 执行一下
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100))
                        .map(x -> "a"+x)
                        .take(5),
                // 延迟0，每100执行一下
                Flux.interval(Duration.ZERO,
                        Duration.ofMillis(100))
                        .map(x -> "b"+x)
                        .take(5),
                (s,f) -> s + " : " + f
        ).subscribe(System.out::println);
        Thread.sleep(1000* 2l);
    }

    @Test
    public void error(){
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);
    }


    private Flux<String> hotFlux1(){
        return flux1().map(i -> {
           //log.info("1111 : {}", i);
            return "[1]-" + i ;
        }).delayElements(Duration.ofMillis(10)) ;
    }

    private Flux<String> hotFlux2(){
        return flux2().map(i -> {
            //log.info("2222 : {}", i);
            return "[2]-" + i ;
        }).delayElements(Duration.ofMillis(4)) ;
    }

    private Flux<Integer> flux1(){
        return Flux.range(1,4) ;
    }

    public Flux<Integer> flux2(){
        return Flux.range(5,8) ;
    }
}
