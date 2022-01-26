package com.yicj.future.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Stream;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 16:45
 **/
@Slf4j
public class FutureTaskTest {


    @Test
    public void test1(){
        // 创建callable线程对象
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable) ;
        // 启动线程
        new Thread(futureTask).start();
        // 获取线程运算结果
        try {
            Integer result = futureTask.get();
            log.info("result : {}", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            return Stream.iterate(1, item -> item +1)
                    .limit(10)
                    .reduce(Integer::sum)
                    .orElse(-1) ;
        }
    }

}
