package com.yicj.kotlin.service.impl;

import com.yicj.kotlin.service.HelloService;
import com.yicj.kotlin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class) ;

    @Autowired
    private HelloService helloService ;

    @Override
    public void saveUser(String name) {
        helloService.hello(name) ;
        log.info("保存用户: {} 的信息", name);
    }

}
