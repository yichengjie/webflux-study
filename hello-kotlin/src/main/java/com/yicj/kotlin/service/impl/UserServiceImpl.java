package com.yicj.kotlin.service.impl;

import com.yicj.kotlin.service.HelloService;
import com.yicj.kotlin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HelloService helloService ;

    @Override
    public void saveUser(String name) {
        helloService.hello(name) ;
        log.info("保存用户: {} 的信息", name);
    }
}
