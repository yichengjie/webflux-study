package com.yicj.reactor.service;

import com.yicj.reactor.model.UserInfo;

import java.util.List;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-07 17:40
 **/
public interface UserService {

    List<UserInfo> loadAllUsers() ;
}
