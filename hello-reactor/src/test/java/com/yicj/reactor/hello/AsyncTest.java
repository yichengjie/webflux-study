package com.yicj.reactor.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AsyncTest {

    @Test
    public void test1() throws InterruptedException {
        log.info("time to get before flux");
        Flux<String> flux = Flux.fromIterable(this.generateIterable())
                .subscribeOn(Schedulers.newSingle("singleT"));
        log.info("time to get after flux");
        flux.subscribe(item -> log.info("item : {}", item)) ;
        Thread.sleep(4000);
    }


    @Test
    public void test2() throws InterruptedException {
        log.info("time to get before flux");
        Flux<String> flux = Flux.defer(() -> Flux.fromIterable(this.generateIterable()))
                .subscribeOn(Schedulers.newSingle("singleT")) ;
        log.info("time to get after flux");
        flux.subscribe(item -> log.info("item : {}", item)) ;
        Thread.sleep(4000);
    }

    private List<String> generateIterable(){
        log.info("start generateIterable ");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> result = new ArrayList<>() ;
        result.add("AAA") ;
        result.add("BBB") ;
        result.add("CCC") ;
        return result;
    }
}
