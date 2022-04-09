package com.yicj.kotlin.controller

import com.yicj.kotlin.service.HelloService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController {

    @Autowired
    lateinit var helloService: HelloService ;

    @GetMapping("/")
    fun hello(): String{
        var retValue = helloService.hello("yicj")
        return retValue
    }
}