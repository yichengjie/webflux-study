package com.yicj.future.hello;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 15:24
 **/
@Slf4j
public class HelloTest {

    @Test
    public void iterate(){
        List<String> resultList = Collections.synchronizedList(Lists.newArrayList());
        List<String> list = Arrays.asList("A","B","C") ;
        Stream.iterate(0, i -> i +1)
                .limit(list.size())
                .forEach(index -> log.info("index : {}, value: {}", index, list.get(index))); ;
    }
}
