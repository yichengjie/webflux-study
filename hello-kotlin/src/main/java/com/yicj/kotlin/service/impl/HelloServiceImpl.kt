package com.yicj.kotlin.service.impl

import com.yicj.kotlin.service.HelloService
import org.springframework.stereotype.Service

@Service
class HelloServiceImpl : HelloService{

    override fun hello(name: String): String {

        return "hello, $name" ;
    }
}