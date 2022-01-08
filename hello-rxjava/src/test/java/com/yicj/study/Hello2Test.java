package com.yicj.study;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

public class Hello2Test {

    @Test
    public void test1() throws InterruptedException {

        Observable.create(e ->{
                    Thread.sleep(10);
                    e.onNext("1");
                    Thread.sleep(10);
                    e.onNext("2");
                    Thread.sleep(10);
                    e.onNext("3");
                    Thread.sleep(10);
                    e.onNext("4");
                    Thread.sleep(10);
                    e.onNext("5");
                    e.onComplete();
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(s -> {
                    try {
                        Thread.sleep(30);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return s +"_map" ;
                })
                .observeOn(Schedulers.computation())
                .subscribe(s ->{
                    try {
                        Thread.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    showInUI(s) ;
                }) ;
        Thread.sleep(1000);

    }

    private void showInUI(String s) {
        System.out.println("showInUi : " + s);
    }
}
