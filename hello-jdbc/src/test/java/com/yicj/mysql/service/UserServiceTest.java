package com.yicj.mysql.service;

import com.yicj.mysql.MybatisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisApplication.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void queryAll() {
        userService.list(null).forEach(user -> log.info("user: {}", user));
    }

}
