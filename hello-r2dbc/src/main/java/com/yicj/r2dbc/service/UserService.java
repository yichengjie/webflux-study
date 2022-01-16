package com.yicj.r2dbc.service;

import com.yicj.r2dbc.entity.UserInfo;
import reactor.core.publisher.Flux;

public interface UserService {
    Flux<UserInfo> findAll() ;
}
