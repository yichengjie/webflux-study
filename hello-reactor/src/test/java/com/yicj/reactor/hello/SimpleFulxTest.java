package com.yicj.reactor.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class SimpleFulxTest {

    @Test
    public void test01(){
        Flux<Integer> flux1 = Flux.just(1);
        Flux<Integer> flux2 = Flux.just(2);
        Flux<Integer> flux3 = Flux.just(3);
        Flux.zip(flux1, flux2, flux3).subscribe((v1) -> {
            log.info("value : {}", v1);
        }) ;
    }

    @Test
    public void test02(){
        List<Flux<Integer>> fluxes = Arrays.asList(Flux.just(1), Flux.just(2), Flux.just(3));

    }
}
