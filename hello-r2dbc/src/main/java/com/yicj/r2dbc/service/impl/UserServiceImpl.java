package com.yicj.r2dbc.service.impl;

import com.yicj.r2dbc.entity.UserInfo;
import com.yicj.r2dbc.repository.UserRepository;
import com.yicj.r2dbc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository ;

    @Override
    public Flux<UserInfo> findAll() {
        Flux<UserInfo> all = userRepository.findAll();
        return all.map(userInfo -> {
            log.info("====> user info : {}", userInfo);
            UserInfo retObj = new UserInfo();
            retObj.setId(userInfo.getId());
            retObj.setName(userInfo.getName());
            retObj.setSex(userInfo.getSex());
            retObj.setEmail(userInfo.getEmail());
            return retObj ;
        }) ;
    }
}
