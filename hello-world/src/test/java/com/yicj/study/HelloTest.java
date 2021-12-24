package com.yicj.study;

import com.yicj.hello.HelloApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-21 09:48
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloApplication.class)
public class HelloTest {

    @Test
    public void test1(){
        int [] nums = {1, 2, 3} ;
        int sum = IntStream.of(nums).parallel().sum() ;
        log.info("结果为 : {}", sum);
    }


    @Test
    public void test2() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");   // 1
        Mono<String> resp = webClient
                .get().uri("/mvc") // 2
                .retrieve() // 3
                .bodyToMono(String.class);  // 4
        resp.subscribe(ret -> log.info("===> ret content : {}", ret));    // 5
        TimeUnit.SECONDS.sleep(6);  // 6
    }

}
