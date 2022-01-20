package com.yicj.func;

import com.yicj.func.model.StudentScore;
import com.yicj.func.model.User;
import com.yicj.func.service.StudentScoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        userFlux.reduce(0, (Integer u1,User u2) -> {
                log.info("u1 : {}, u2: {}", u1, u2);
                return u1 + u2.getAge() ;
            })
            .subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void reduce2(){
        List<User> userList = Arrays.asList(
                User.builder().username("张三").age(11).build(),
                User.builder().username("李四").age(12).build(),
                User.builder().username("王五").age(13).build(),
                User.builder().username("赵六").age(14).build()
        ) ;

        Flux<User> userFlux = Flux.fromIterable(userList);
        userFlux.reduce((u1,u2) -> {
                //log.info("u1 : {}, u2: {}", u1, u2);
                return User.builder().username("汇总").age(u1.getAge() + u2.getAge()).build() ;
            })
            .subscribe(value -> log.info("value : {}", value)) ;
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

        Stream<Integer> stream1 = Stream.of(1, 2);
        Stream<Integer> stream2 = Stream.of(3, 4);
    }

    @Test
    public void generate(){
//        Flux.generate(sink -> {
//            sink.next("Hello");
//            sink.complete();
//        }).subscribe(System.out::println);
        List<Integer> retList = new ArrayList<>() ;
        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
    }

    @Test
    public void map(){
        List<StudentScore> studentList = new StudentScoreService().buildStudentScoreList();
        Flux<StudentScore> studentFlux = Flux.fromIterable(studentList);
        //
        studentFlux.filter(student -> "语文".equals(student.getSubject()))
                .map(student -> {
                    User user = new User();
                    user.setUsername(student.getStuName());
                    return user ;
                }).subscribe(user -> {
                    String userName = user.getUsername() ;
                    log.info("userName : {}", userName);
                    log.info("user : {}", user);
                });

    }

}
