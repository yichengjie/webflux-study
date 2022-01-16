package com.yicj.mysql.controller;

import com.yicj.mysql.entity.UserInfo;
import com.yicj.mysql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping("/user/findList")
    public Flux<UserInfo> findList(@RequestBody UserInfo userInfo){
        log.info("===> findList method exec start ....");
        Flux<UserInfo> flux = Flux.create(emitter -> {
            List<UserInfo> list = userService.findList(userInfo);
            for (UserInfo user : list) {
                emitter.next(user);
            }
            emitter.complete();
        });

        flux.subscribe(new Subscriber<UserInfo>() {
            private Subscription subscription ;
            @Override
            public void onSubscribe(Subscription subscription) {
                log.info("onSubscribe ..");
                this.subscription = subscription ;
                this.subscription.request(1);
            }

            @Override
            public void onNext(UserInfo userInfo) {
                log.info("onNext : {}", userInfo);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("onError ..");
            }

            @Override
            public void onComplete() {
                log.info("onComplete ..");
            }
        }) ;

        log.info("===> findList method exec end ....");

        return flux ;
    }
}
