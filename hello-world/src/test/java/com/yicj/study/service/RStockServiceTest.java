package com.yicj.study.service;

import com.yicj.hello.HelloApplication;
import com.yicj.hello.model.Stock;
import com.yicj.hello.service.RStockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloApplication.class)
public class RStockServiceTest {

    @Autowired
    private RStockService stockService ;

    @Test
    public void getAllStock(){
        List<Stock> list = stockService.getAllStock();
        log.info("list : {}", list);
    }


    @Test
    public void getAllStockAsync() throws Exception {
        Future<List<Stock>> future = stockService.getAllStockAsync();
        log.info("list : {}", future.get());
    }

    @Test
    public void getAllStockAsync2() throws Exception{
        Future<List<Stock>> future = stockService.getAllStockAsync2();
        log.info("list : {}", future.get());
    }

}
