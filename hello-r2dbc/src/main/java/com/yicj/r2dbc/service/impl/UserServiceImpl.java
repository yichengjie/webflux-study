package com.yicj.r2dbc.service.impl;

import com.yicj.r2dbc.entity.UserInfo;
import com.yicj.r2dbc.repository.UserRepository;
import com.yicj.r2dbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository ;

    @Override
    public Flux<UserInfo> findAll() {
        Flux<UserInfo> all = userRepository.findAll();
        return all;
    }
}
