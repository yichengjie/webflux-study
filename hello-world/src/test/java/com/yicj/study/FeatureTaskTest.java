package com.yicj.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

@Slf4j
public class FeatureTaskTest {

    @Test
    public void hello() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Runnable runnable = () -> {
            log.info("start .....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("end .....");
        } ;
        Future<?> submit = pool.submit(runnable);
        Object o = submit.get();
        log.info("result : {} ", o);
    }


    @Test
    public void hello2() throws ExecutionException, InterruptedException {
        Runnable runnable = () -> {
            log.info("start .....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("end .....");
        } ;
        FutureTask<Void> futureTask = new FutureTask<Void>(runnable, null) ;
        Thread thread = new Thread(futureTask);
        thread.start();
        Object o = futureTask.get();
        log.info("result : {} ", o);
    }
}
