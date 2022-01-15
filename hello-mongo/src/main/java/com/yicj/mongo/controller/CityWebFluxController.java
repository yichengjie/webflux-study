package com.yicj.mongo.controller;

import com.yicj.mongo.handler.CityHandler;
import com.yicj.mongo.model.City;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/city")
public class CityWebFluxController {
    @Autowired
    private CityHandler cityHandler;

    @GetMapping(value = "/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        Mono<City> byId = cityHandler.findCityById(id);
        Subscriber subscriber = new Subscriber<City>() {
            private Subscription subscription ;
            @Override
            public void onSubscribe(Subscription s) {
                this.subscription = s ;
                log.info("onSubscribe ....");
                s.request(1);
            }

            @Override
            public void onNext(City city) {
                log.info("onNext : {}", city);
                this.subscription.request(1);
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
        byId.subscribe(subscriber);
        return byId ;
    }

    @GetMapping()
    public Flux<City> findAllCity() {
        log.info("main : findAllCity().... ");
        return cityHandler.findAllCity();
    }

    @PostMapping()
    public Mono<City> saveCity(@RequestBody City city) {
        return cityHandler.save(city);
    }

    @PutMapping()
    public Mono<City> modifyCity(@RequestBody City city) {
        return cityHandler.modifyCity(city);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        return cityHandler.deleteCity(id);
    }
}
