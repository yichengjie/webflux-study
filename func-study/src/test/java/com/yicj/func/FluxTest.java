package com.yicj.func;

import com.yicj.func.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-17 16:40
 **/
@Slf4j
public class FluxTest {

    @Test
    public void monoZip(){
        Mono<String> first = Mono.just("张三") ;
        Mono<String> second = Mono.just("李四") ;

        Mono<Tuple2<String, String>> zip = Mono.zip(first, second);
        zip.map(tuple ->{
            String t1 = tuple.getT1();
            String t2 = tuple.getT2();
            return t1 + " , " + t2 ;
        }).subscribe(value ->{
            log.info("value : " + value);
        }) ;
        log.info("----------------------------");
        zip.subscribe(value ->{
            log.info("value : " + value);
        }) ;
    }

    @Test
    public void reduce(){

        List<User> userList = Arrays.asList(
                User.builder().username("张三").age(11).build(),
                User.builder().username("李四").age(12).build(),
                User.builder().username("王五").age(13).build(),
                User.builder().username("赵六").age(14).build()
        ) ;

        Flux<User> userFlux = Flux.fromIterable(userList);

    }

}
