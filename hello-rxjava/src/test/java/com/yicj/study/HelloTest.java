package com.yicj.study;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.util.Objects;

public class HelloTest {

    @Test
    public void test1(){

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    emitter.onNext(getFilePath());
                    emitter.onComplete();
                })
                // 指定了getFilePath()执行的线程环境
                .subscribeOn(Schedulers.newThread())
                // 将接下来指定map()运行在io线程
                .map((Function<String, Bitmap>) s ->{
                    // createBitmapFromPath(s) 方法
                    // 显然这是一个极其耗时的操作
                    return createBitmapFromPath(s) ;
                })
                .filter(Objects::nonNull)
                // 将接下来的线程执行环境指定为主线程
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }) ;


    }

    private Bitmap createBitmapFromPath(String s) {

        return null ;
    }

    class Bitmap{

    }

    private String getFilePath() {

        return null ;
    }
}
