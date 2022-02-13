package com.yicj.study;

import com.sun.codemodel.internal.JVar;
import com.yicj.study.model.Pair;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.reactivex.Observable.*;

@Slf4j
public class Hello3Test {

    @Test
    public void tryCatch(){
        Observable.create(subscribe ->{
            try {
                subscribe.onNext(1);
                int i = 1/0 ;
                subscribe.onComplete();
            }catch (Exception e){
                subscribe.onError(e);
            }
        }).subscribe(value ->{
            log.info("next value : {}", value);
        }, error ->{
            log.error("===> error :" ,error);
        }, () ->{
            log.info("complete ! ");
        }) ;
    }

    @Test
    public void timer() throws InterruptedException {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe((Long zero) -> log.info("value : {}", zero));
        Thread.sleep(2000);
    }

    @Test
    public void intervalTest() throws InterruptedException {
        Observable.interval(1_000_000/60, TimeUnit.MICROSECONDS)
                .subscribe(value -> log.info("value : {}", value)) ;
        Thread.sleep(3000);
    }

    @Test
    public void flatMap(){
        List<String> list = Arrays.asList("张三","李四","王五","赵六") ;
        Observable.fromIterable(list)
                .flatMap(item -> {
                    String retVal = "ret["+item+"]" ;
                    log.info("next value : {}", item);
                    return Observable.just(retVal) ;
                },2)
                .subscribe() ;

    }

    @Test
    public void zip(){

        Observable<Integer> oneToEight = Observable.range(1, 8);
        Observable<String> ranks = oneToEight.map(Object::toString) ;
//        ranks.doOnNext(context -> log.info("context: {}", context))
//                .subscribe() ;

        Observable<String> files = oneToEight
                .map(x -> 'a' + x - 1)
                .map(ascii -> (char) ascii.intValue())
                .map(ch -> Character.toString(ch));

        Observable<String> squares =
                files.flatMap(file -> ranks.map(rank -> file + rank));

        //squares.subscribe(value -> log.info("value : {}", value)) ;
        squares.forEach(item -> log.info("value : {}", item)) ;
    }


    @Test
    public void combineLast() throws InterruptedException {
        Observable.combineLatest(
                interval(17, TimeUnit.MICROSECONDS).map(x -> "S" + x),
                interval(10, TimeUnit.MICROSECONDS).map(x -> "F" + x),
                (s, f) -> f + ":" + s
        ).forEach(value -> log.info("value : {}", value)) ;
        Thread.sleep(10);
    }


    @Test
    public void scan(){
        Observable<Long> progress = Observable.just(10L, 14L, 12L, 13L, 14L, 16L);
        //
        progress.scan((total,chunk) -> total + chunk)
                .subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void reduce(){
        Observable<Long> progress = Observable.just(10L, 14L, 12L, 13L, 14L, 16L);
        //
        progress.reduce((total,chunk) -> total + chunk)
                .subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void single(){
        Observable<Long> progress = Observable.just(10L, 14L, 12L, 13L, 14L, 16L);
        Single<Long> single = progress.single(9L);
        single.subscribe(value -> log.info("value : {}", value)) ;
    }


    @Test
    public void flatMapDelay() throws InterruptedException {
        Observable.fromArray("张三","李四","王五")
                .doOnNext(context -> log.info("context : {}", context))
                .flatMap(item -> Observable.just(item).delay(1,TimeUnit.SECONDS))
                .subscribe(value -> log.info("value : {}", value)) ;

        TimeUnit.SECONDS.sleep(5);
    }

    public Observable<String> speak(String quote, long millisPerChar){
        String [] tokens = quote.replaceAll("[:,]", "")
                .split(" ") ;
        Observable<String> words = Observable.fromArray(tokens);
        Observable<Long> absoluteDelay = words.map(String::length)
                .map(len -> len * millisPerChar)
                .scan((total, current) -> total + current);
        //

        return words.zipWith(absoluteDelay.startWith(0L), Pair::of)
                .flatMap(
                        pair -> just(pair.getLeft())
                                .delay(pair.getRight(), TimeUnit.MICROSECONDS)
                );
    }

    @Test
    public void blockList(){
        Observable<String> observable = Observable.defer(() -> Observable.create(sink -> {
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("张三");
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("李四");
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("王五");
            sink.onComplete();
        }));
        //observable.subscribe(value -> log.info("value : {}", value)) ;
        Single<List<String>> listPerson = observable.toList();
        List<String> personList = listPerson.blockingGet();
        log.info("person list : {}", personList);
    }

    @Test
    public void blockList2(){
        Observable<String> observable = Observable.create(sink -> {
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("张三");
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("李四");
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("王五");
            sink.onComplete();
        }) ;
        Single<List<String>> listPerson = observable.toList();
        List<String> personList = listPerson.blockingGet();
        log.info("person list : {}", personList);
    }

    @Test
    public void list1() throws InterruptedException {
        Observable<String> observable = create(sink -> {
            log.info("init ....");
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("张三");
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("李四");
            TimeUnit.SECONDS.sleep(1);
            sink.onNext("王五");
            // 这里onComplete不要漏掉了
            sink.onComplete();
        });
        Thread.sleep(3000);
        //observable.subscribe(value -> log.info("value : {}", value));
    }

    @Test
    public void fromIterator() throws InterruptedException {
        //Observable<String> listObservable = fromIterable(listPerson()) ;
        //Observable<String> arrayObservable = fromArray(arrayPerson());
        Observable.defer(() -> Observable.fromIterable(listPerson())) ;
        Thread.sleep(1000);
    }

    private String [] arrayPerson(){
        log.info("init array person ....");
        String [] persons = new String[3] ;
        persons[0] = "张三" ;
        persons[1] = "李四" ;
        persons[2] = "王五" ;
        return persons;
    }

    private List<String> listPerson(){
        List<String> list = new ArrayList<>() ;
        log.info("init list person ....");
        list.add("张三") ;
        list.add("李四") ;
        list.add("王五") ;
        return list ;
    }
}
