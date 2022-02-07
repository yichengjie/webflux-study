package com.yicj.reactor.service.impl;

import com.yicj.reactor.model.UserInfo;
import com.yicj.reactor.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-07 17:43
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserInfo> loadAllUsers() {
        log.info("load all users start. ...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<UserInfo> userInfoList = IntStream.range(1, 10).mapToObj(i -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("username[" + i + "]");
            userInfo.setAge(10 + i);
            userInfo.setAddress("address[" + i + "]");
            return userInfo;
        }).collect(Collectors.toList());
        log.info("load all users end. ...");
        return userInfoList;
    }
}
