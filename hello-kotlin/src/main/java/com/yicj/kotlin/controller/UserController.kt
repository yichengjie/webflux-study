package com.yicj.kotlin.controller

import com.yicj.kotlin.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService ;

    @GetMapping("/hello")
    fun hello(): String{
        var name = "yicj"
        userService.saveUser(name)
        return "hello" ;
    }
}