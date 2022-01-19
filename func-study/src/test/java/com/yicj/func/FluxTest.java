package com.yicj.func;

import cn.hutool.core.util.StrUtil;
import com.yicj.func.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        userFlux.reduce(0, (u1,u2) -> u1) ;
    }

    @Test
    public void window(){
        Flux<Flux<Integer>> window = Flux.range(1, 10).window(3);
        window.subscribe(integerFlux -> {
            // 这里会卡死
            //List<Integer> list = integerFlux.collectList().block();
            // 这里能正常运行
            //Mono<List<Integer>> listMono = integerFlux.collectList();
            //listMono.subscribe(value -> log.info("value : {}", value)) ;
            //
            integerFlux.map(String::valueOf)
                    .reduce("", (a,b) -> String.join(",", a,b))
                    .subscribe(value -> log.info("value : {}", value.substring(2)));
            System.out.println("-------------------");
        });
    }

    @Test
    public void collectList(){
        Flux<Integer> integerFlux = Flux.fromIterable(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> list = integerFlux.collectList().block();
        log.info("list : {}", list);
        log.info("-----------------------------------");
        List<Integer> streamList = integerFlux.toStream().collect(Collectors.toList());
        log.info("stream list : {}", streamList);
    }

    //https://dandelioncloud.cn/article/details/1439686236066902018
    @Test
    public void merge(){
//        Flux.merge(Flux.just(0, 1, 2, 3), Flux.just(7, 5, 6), Flux.just(4, 7), Flux.just(4, 7))
//                .toStream()
//                .forEach(item -> log.info("value : {}", item));
        //////////
        Flux.merge(Flux.just(0, 1, 2, 3),
                Flux.just(7, 5, 6),
                Flux.just(4, 7),
                Flux.just(4, 7))
        .subscribe(value -> log.info("value : {}", value));
    }

}
