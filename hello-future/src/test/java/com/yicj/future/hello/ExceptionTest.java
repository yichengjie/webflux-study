package com.yicj.future.hello;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 18:33
 **/
public class ExceptionTest {

    /**
     * 4月2号时发现内部框架在测试时会超时,检查流程计数器发现计数器有流程未减数,
     * 利用编译原理重写该计数类发现流程名字,调试得知在当时流程返回了null，
     * 导致future一直未完成，直到超时，问题已反馈给架构修复
     */
    @Test
    public void test01(){
        CompletableFuture<String> future = new CompletableFuture();
        future.complete(null);//流程中返回的响应null
        future = future.thenCompose((s)-> {
            //s=null
            if (true){//模拟问题发生
                throw new NullPointerException();
            }
            return CompletableFuture.completedFuture(s);//不会执行
        });
        CompletableFuture.allOf(new CompletableFuture[]{future}).thenRunAsync(()->{
            System.out.println("allThenRunAsync");//不会执行
        });
    }


    /**
     * 加入异常捕获
     */
    @Test
    public void test02(){
        CompletableFuture<String> future = new CompletableFuture();
        future.complete(null);//流程中返回的响应null
        future = future.thenCompose((s)-> {
            //s=null
            if (true){//模拟问题发生
                throw new NullPointerException();
            }
            System.out.println("NullPointerException");
            return CompletableFuture.completedFuture(s);//不会执行
        }).exceptionally(throwable -> {
            System.out.println(throwable);
            return "exeption";
        });
        //final String result = future.join();
        //System.out.println(result);
        CompletableFuture.allOf(new CompletableFuture[]{future}).thenRunAsync(()->{
            System.out.println("allThenRunAsync");//不会执行
        });
    }
}
