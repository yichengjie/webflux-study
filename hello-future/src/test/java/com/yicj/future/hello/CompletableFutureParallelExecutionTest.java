package com.yicj.future.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 17:25
 **/
@Slf4j
public class CompletableFutureParallelExecutionTest {

    /**
     * CompletableFuture实现并行化操作
     * 同时从新浪和网易查询证券代码，只要任意一个返回结果，就进行下一步查询价格，
     * 查询价格也同时从新浪和网易查询，只要任意一个返回结果，就完成操作。
     */
    @Test
    public void parallelExecution() throws InterruptedException {
        String name1 = "中国石油1" ;
        String url1 = "www.sina.com" ;
        String name2 = "中国石油2" ;
        String url2 = "www.yicj.com" ;

        CompletableFuture<Object> cf = CompletableFuture.anyOf(
                CompletableFuture.supplyAsync(() -> queryCode(name1, url1)),
                CompletableFuture.supplyAsync(() -> queryCode(name2, url2))
        );
        // 同时根据编码从新浪和网易查询价格
        CompletableFuture<Object> cf2 = CompletableFuture.anyOf(
                cf.thenApplyAsync(code -> {
                    log.info("===> code : {}", code);
                    return getPrice((String) code, "sina");
                }),
                cf.thenApplyAsync(code -> {
                    log.info("===> code : {}", code);
                    return getPrice((String) code, "yicj");
                }),
                cf.thenApplyAsync(code -> {
                    log.info("===> code : {}", code);
                    return getPrice((String) code, "xxx");
                })
        );
        // 只要任意一个返回结果，就完成操作
        cf2.thenAccept(result -> {
            log.info("result : {}", result);
        }) ;
        // 主线程不要立刻结束，否则CompletableFuture默认使用线程池会立刻关闭
        Thread.sleep(2000);
    }


    public static String queryCode(String name, String url){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            log.info("查询编码 : {}", url + name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "2022: " + Math.random() * 10 ;
    }

    public static String getPrice(String code, String url){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            log.info("查询价格: {}", url + code);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return url + Math.random() * 100 ;
    }

}
