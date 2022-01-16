package com.yicj.r2dbc.controller;

import com.yicj.r2dbc.entity.UserInfo;
import com.yicj.r2dbc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class UserInfoController {

    @Autowired
    private UserService userService ;

    @GetMapping("/user/findAll")
    public Flux<UserInfo> findAll(){
        log.info("==> controller findAll ....");
        return userService.findAll();
    }

}
