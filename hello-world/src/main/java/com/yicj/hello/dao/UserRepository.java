package com.yicj.hello.dao;

import com.yicj.hello.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepository {

    public Mono<String> saveUser(Mono<UserInfo> userInfo){
        userInfo.subscribe(new Subscriber<UserInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe ....");
            }

            @Override
            public void onNext(UserInfo userInfo) {
                log.info("onNext : 保存用户信息到数据库【开始】");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String uuid = UUID.randomUUID().toString();
                userInfo.setId(uuid);
                log.info("onNext : 保存用户信息到数据库【结束】");
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError : 保存入库报错...");
            }

            @Override
            public void onComplete() {
                log.info("onComplete : 保存用户数据到数据库完成..");
            }
        });
        return null ;
    }

}
