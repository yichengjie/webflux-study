package com.yicj.func;

import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-11 09:39
 **/
public class StreamTest {

    @Test
    public void reduceTest() {
        ArrayList<Integer> newList = new ArrayList<>();

        ArrayList<Integer> accResult_ = Stream.of(2, 3, 4)
                .reduce(newList, (acc, item) -> {
                            acc.add(item);
                            System.out.println("item: " + item);
                            System.out.println("acc+ : " + acc);
                            System.out.println("BiFunction");
                            return acc;
                        }, (acc, item) -> null);
        System.out.println("accResult_: " + accResult_);
        System.out.println("newList: " + newList);
    }
}
