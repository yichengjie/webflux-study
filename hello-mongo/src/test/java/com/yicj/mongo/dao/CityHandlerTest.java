package com.yicj.mongo.dao;

import com.yicj.mongo.MongoApplication;
import com.yicj.mongo.handler.CityHandler;
import com.yicj.mongo.model.City;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Scanner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class CityHandlerTest {

    @Autowired
    private CityHandler cityHandler ;

    @Test
    public void test1(){
        Long id = 1001L ;
        Mono<City> cityMono = cityHandler.findCityById(id);

        Mono<City> mono = Mono.fromDirect(cityMono);

        Subscriber subscriber = new Subscriber<City>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
                log.info("onSubscribe ....");
            }
            @Override
            public void onNext(City city) {
                log.info("onNext : {}", city);
            }
            @Override
            public void onError(Throwable t) {
                log.error("onError ..", t);
            }
            @Override
            public void onComplete() {
                log.info("onComplete ....");
            }
        } ;
        mono.subscribe(subscriber);
        new Scanner(System.in).next() ;
    }
}
