package com.yicj.reactor.service;

import com.yicj.reactor.model.UserInfo;
import com.yicj.reactor.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-07 17:38
 **/
@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ReactorApplication.class)
public class UserServiceTest {

    //@Autowired
    private UserService userService = new UserServiceImpl();

    @Test
    public void test1() throws InterruptedException {
        Flux.fromIterable(userService.loadAllUsers())
                .subscribeOn(Schedulers.newElastic("subscribe-on-"))
                .map(UserInfo::getUsername)
                .log()
                .publishOn(Schedulers.newElastic("publish-on-"))
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnNext(String value) {
                        log.info("value : {}", value);
                    }
                });

        Thread.sleep(3000);
    }

    @Test
    public void test2() throws InterruptedException {
         Flux.defer(() -> Flux.fromIterable(userService.loadAllUsers()))
                .subscribeOn(Schedulers.newElastic("subscribe-on-"))
                .map(UserInfo::getUsername)
                .log()
                .publishOn(Schedulers.newElastic("publish-on-"))
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnNext(String value) {
                       log.info("value : {}", value);
                    }
                });

         Thread.sleep(3000);
    }


}
