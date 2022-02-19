package com.yicj.hello.controller;

import com.yicj.hello.properties.UserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class HelloController {

    @Autowired
    private UserResource userResource ;

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.just("hello world") ;
    }

    @GetMapping("/world")
    public ModelAndView world(){
        ModelAndView mv = new ModelAndView() ;
        mv.addObject("userResource", userResource) ;
        mv.setViewName("world");
        return mv ;
    }

}
