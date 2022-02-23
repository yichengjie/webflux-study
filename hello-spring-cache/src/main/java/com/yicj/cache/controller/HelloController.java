package com.yicj.cache.controller;

import com.yicj.cache.model.UserInfo;
import com.yicj.cache.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executor;

/**
 * @description:
 * @author: yicj1
 * @create: 2022-02-17 15:03
 **/
@Slf4j
@RestController
public class HelloController {

    @Autowired
    private Executor asyncServiceExecutor ;

    @Autowired
    private HelloService helloService ;

    @GetMapping("/hello/mono")
    public Mono<String> hello(){
        log.info("hello ...");
        return Mono.fromCallable(()-> {
                    log.info("mono execute ...");
                    Thread.sleep(3000);
                    return "hello world" ;
                }).subscribeOn(Schedulers.fromExecutor(asyncServiceExecutor)) ;
                //.subscribeOn(Schedulers.boundedElastic().("test-")) ;
    }

    @GetMapping("/hello/normal")
    public String helloNormal() throws InterruptedException {
        log.info("hello ...");
        log.info("mono execute ...");
        Thread.sleep(3000);
        return "hello world" ;
    }

    @GetMapping("/hello/cache")
    public String helloCache(String name, String address){
        return helloService.hello(name, address) ;
    }

    @GetMapping("/hello/cache/immediate")
    public String doHelloCache(String name, String address){
        return helloService.doHello(name, address) ;
    }

    @GetMapping("/hello/user/{id}")
    public UserInfo helloUserInfo(@PathVariable("id") String id){
        return helloService.findById(id) ;
    }
}
