package com.yicj.func;

import com.yicj.func.model.User;
import com.yicj.func.service.ICalculator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
public class HelloLambdaTest {

    @Test
    public void test01(){
        ICalculator ic = i -> i * i ;
        int square = ic.square(5) ;
        log.info("square: {}", square);
        /////////////////////////
        Function<Integer,Integer> func = i -> i * i;
        int square2= func.apply(5) ;
        log.info("square2: {}",square2);
    }

    @Test
    public void test02(){
        ICalculator ic = (int i) -> i * i ;
        int square = ic.square(5) ;
        log.info("square : {}", square);
    }

    @Test
    public void test03(){
        User user = new User() ;
        user.setUsername("张三");
        String say = user.say(username -> "hello " + username);
        log.info("say : {}", say);
    }


    @Test
    public void test04(){


    }
}
