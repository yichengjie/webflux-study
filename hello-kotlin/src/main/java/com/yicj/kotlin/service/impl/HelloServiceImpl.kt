package com.yicj.kotlin.service.impl

import com.yicj.kotlin.service.HelloService
import com.yicj.kotlin.utils.CommonUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HelloServiceImpl : HelloService{
    @Autowired
    lateinit var utils: CommonUtils

    override fun hello(name: String): String {
        return utils.helloName(name)
    }
}