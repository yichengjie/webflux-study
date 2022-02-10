package com.yicj.reactor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-10 09:55
 **/
@Slf4j
@RestController
public class BuzController {

    /*@ExceptionHandler({NullPointerException.class})
    public String exception(NullPointerException e){
        log.error("error : ", e);
        return "null pointer exception" ;
    }*/

    @GetMapping("/test")
    public void test(){
        throw new NullPointerException("出错啦!") ;
    }

}
