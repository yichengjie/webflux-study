package com.yicj.hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.just("hello world") ;
    }

    @GetMapping("/world")
    public ModelAndView world(){
        ModelAndView mv = new ModelAndView() ;
        mv.addObject("user", "yicj") ;
        mv.setViewName("world");
        return mv ;
    }

}
