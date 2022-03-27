package com.yicj.hello.service.impl;

import com.yicj.hello.model.Stock;
import com.yicj.hello.service.RStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Slf4j
@Service
public class RStockServiceImpl implements RStockService {

    @Override
    public List<Stock> getAllStock() {
        log.info("get all stock start ...... ");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("get all stock end ...... ");
        return Arrays.asList(new Stock("鸡蛋", 5));
    }

    @Async
    @Override
    public Future<List<Stock>> getAllStockAsync() {
        FutureTask<List<Stock>> task = new FutureTask<>(this::getAllStock);
        new Thread(task).start();
        return task ;
    }

    @Async
    @Override
    public Future<List<Stock>> getAllStockAsync2() {
        return new AsyncResult<>(getAllStock());
    }
}
