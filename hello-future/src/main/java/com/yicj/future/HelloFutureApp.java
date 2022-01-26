package com.yicj.future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 09:59
 **/
@RestController
@SpringBootApplication
public class HelloFutureApp {

    public static void main(String[] args) {

        SpringApplication.run(HelloFutureApp.class, args) ;
    }

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.defer(() -> Mono.just("hello world")) ;
    }
}
