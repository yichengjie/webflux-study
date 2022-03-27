package com.yicj.hello.controller;

import com.yicj.hello.model.Stock;
import com.yicj.hello.service.RStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

@Controller
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private RStockService stockService ;


    @GetMapping("/getStocksDeferredResult")
    public DeferredResult<String> getStocksDeferredResult(Model model){
        DeferredResult<String> result = new DeferredResult<>() ;
        // 注意这里DeferredResult 需要自己去执行，并设置结果
        ForkJoinPool.commonPool().submit(() ->{
            try {
                List<Stock> list = stockService.getAllStockAsync2().get();
                model.addAttribute("stocks", list) ;
                result.setResult("subscription") ;
            }catch (Exception e){
                result.setErrorResult(e) ;
            }
        }) ;
        return result ;
    }

    @GetMapping("/getStocksCallable")
    public Callable<String> getStocksCallable(Model model){
        Callable<String> callable = () -> {
            List<Stock> list = stockService.getAllStockAsync2().get();
            model.addAttribute("stocks", list) ;
            return "stocks" ;
        } ;
        return callable ;
    }

    @GetMapping("/getStocksWebAsyncTask")
    public WebAsyncTask<String> getStocksWebAsyncTask(Model model){
        Callable<String> callable = () -> {
            List<Stock> list = stockService.getAllStockAsync2().get();
            model.addAttribute("stocks", list) ;
            return "stocks" ;
        } ;
        return new WebAsyncTask<>(callable) ;
    }

}
