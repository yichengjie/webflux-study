package com.yicj.kotlin.controller

import com.yicj.kotlin.model.UserDO
import com.yicj.kotlin.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    var log: Logger = LoggerFactory.getLogger(UserController::class.java) ;

    @Autowired
    lateinit var userService: UserService ;

    @GetMapping("/hello")
    fun hello(): String{
        var userDO = UserDO(name = "张三", age = 18)
        //userDO.name = "张三"
        //userDO.age = 20
        log.info("user do : {}", userDO)
        var name = "yicj"
        userService.saveUser(name)
        return "hello" ;
    }
}