package com.yicj.future.hello;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 10:04
 **/
@Slf4j
public class SimpleTest {

    /**
     * 注意：
     * handle 和 thenApply方法的区别
     * 它们与handle方法的区别在于handle方法会处理正常计算值和异常，因此它可以屏蔽异常，避免异常继续抛出。
     * 而thenApply方法只是用来处理正常值，因此一旦有异常就会抛出。
     */
    private ExecutorService pool = new ThreadPoolExecutor(
            20,40,100, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy()
    ) ;

    //CompletableFuture 串行操作
    @Test
    public void completedFuture(){
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "hello ")
                .thenApply(str -> str + "world ")
                .thenCombine(CompletableFuture.completedFuture("java"), (s1, s2) -> s1 + s2)
                .thenAccept(value -> log.info("value : {}", value));
        future.join() ;
    }


    /**
     * obtrudeException 主动抛出异常
     * 1. 如果将主动抛异常时间延长到6s,
     * 由于该节点计算完成, 则不会收到异常
     * 收到异常:手动失败
     */
    @Test
    public void test02() throws InterruptedException {
        CompletableFuture<String> fu = CompletableFuture.supplyAsync(() ->{
            try {
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ignore){
                // ignore
            }
            return null ;
        }) ;

        CompletableFuture<String> end = fu.exceptionally(throwable -> {
            log.info("收到异常: {}", throwable.getMessage());
            return null;
        });

        TimeUnit.SECONDS.sleep(3);
        // 如果等6秒后再抛，由于结果已经计算完成，则不会收到异常
        //TimeUnit.SECONDS.sleep(6);
        // 手动抛出一个异常，若结果未计算完成，则会抛出。
        fu.obtrudeException(new RuntimeException("手动失败"));
        try {
            String result = end.join() ;
            log.info("result : {}", result);
        }catch (CompletionException e){
            log.error("error : {}", e.getMessage());
        }
    }


    //join和get的区别
    @Test
    public void test03(){
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 1;
        });
        CompletableFuture.allOf(f1).join() ;
        //// join()抛出包装的CompletionException，本质还是内部发生的异常，不需要手动try..catch";
        /**
         * java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
         * Caused by: java.lang.ArithmeticException: / by zero
         * 	at com.yicj.future.hello.SimpleTest.lambda$test03$6(SimpleTest.java:80)
         * 	... 6 more
         */
    }

    @Test
    public void test04(){
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 1;
        });
        try {
            Integer result = f1.get();
            System.out.println("result: " + result);
            System.out.println("get()检查异常需要手动处理try..catch");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * java.util.concurrent.ExecutionException: java.lang.ArithmeticException: / by zero
         * 	at com.yicj.future.hello.SimpleTest.test04(SimpleTest.java:100)
         * Caused by: java.lang.ArithmeticException: / by zero
         * 	at com.yicj.future.hello.SimpleTest.lambda$test04$7(SimpleTest.java:96)
         */
    }

    @Test
    public void test05(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(result -> result + " resultB")
                // 任务C抛出异常
                .thenApply(result -> {
                    throw new RuntimeException();
                })
                // 处理任务C 的返回值或异常
                .handle(new BiFunction<Object, Throwable, Object>() {
                    @Override
                    public Object apply(Object obj, Throwable throwable) {
                        if (throwable != null) {
                            return "errorResultC";
                        }
                        return obj;
                    }
                })
                .thenApply(result -> result + " resultD");
        log.info("result : {}", future.join());
        // result : errorResultC resultD
    }

    @Test
    public void test06(){
        CompletableFuture<String> fa = CompletableFuture.supplyAsync(() -> "resultA");
        CompletableFuture<Integer> fb = CompletableFuture.supplyAsync(() -> 123);
        CompletableFuture<String> fc = CompletableFuture.supplyAsync(() -> "resultC");
        //
        CompletableFuture<Void> f1 = CompletableFuture.allOf(fa, fb, fc);
        // 这里使用join()直到所有的任务执行结束
        Void j1 = f1.join();
        log.info("join one : {}", j1);

        CompletableFuture<Object> f2 = CompletableFuture.anyOf(fa, fb, fc);
        // join()方法会返回最先完成的任务结果，所以它的泛型是Object，因为每个任务可能返回的类型不同
        Object j2 = f2.join();
        log.info("join two : {}", j2);
    }

    /**
     * either 方法
     * 各个带 either 的方法，表达的都是一个意思，指的是两个任务中的其中一个执行完成，就执行指定的操作
     * 它们几组的区别也很明显，分别用于表达是否需要任务 A 和任务 B 的执行结果，是否需要返回值
     * 注意：
     * 1、cfA.acceptEither(cfB, result -> {}); 和 cfB.acceptEither(cfA, result -> {}); 是一个意思；
     * 2、第二个变种，加了 Async 后缀的方法，代表将需要执行的任务放到 ForkJoinPool.commonPool() 中执行(非完全严谨)；
     *    第三个变种很好理解，将任务放到指定线程池中执行；
     * 3、难道第一个变种是同步的？不是的，而是说，它由任务 A 或任务 B 所在的执行线程来执行，取决于哪个任务先结束。
     */
    @Test
    public void test07(){
        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> "resultA");
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> "resultB");

        cfA.acceptEither(cfB, result -> {});
        cfA.acceptEitherAsync(cfB, result -> {});
        cfA.acceptEitherAsync(cfB, result -> {}, pool);

        cfA.applyToEither(cfB, result -> {return result;});
        cfA.applyToEitherAsync(cfB, result -> {return result;});
        cfA.applyToEitherAsync(cfB, result -> {return result;}, pool);

        cfA.runAfterEither(cfA, () -> {});
        cfA.runAfterEitherAsync(cfB, () -> {});
        cfA.runAfterEitherAsync(cfB, () -> {}, pool);
    }

    /**
     * thenCombine 合并任务
     * 把两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
     */
    @Test
    public void test08(){
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello ");
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 120);
        CompletableFuture<String> result = f1.thenCombine(f2, (t, u) -> t + u);
        log.info("result : {}", result.join());
    }

    /**
     * thenCompose 将第一个future的返回结果传入第二个future中执行
     * thenApply（）转换的是泛型中的类型，是同一个CompletableFuture，相当于将CompletableFuture<T> 转换成CompletableFuture<U>
     * thenCompose（）用来连接两个CompletableFuture，是生成一个新的CompletableFuture。
     */
    @Test
     public void test09(){
         CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
             int t1 = new Random().nextInt(10);
             log.info("t1 = {}", t1);
             return t1;
         }).thenCompose(t -> CompletableFuture.supplyAsync(() -> {
             return 100 + t;
         }));
         log.info("result : {}", future.join());
     }

    /**
     * 异常处理
     * 1. handle..()        不会抓获异常，所以配置多个都会被执行;
     * 2. exceptionally()   会抓获异常所以只生效一次。
     * 3. exceptionally()   必须在结束前调用，否则不生效
     *
     *我是1号异常处理器
     *我是3号异常处理器
     *我是2号异常处理器
     *我是4号异常处理器
     */
     @Test
     public void test10(){
         CompletableFuture<Object> future = CompletableFuture.runAsync(() -> {
             throw new RuntimeException("异常");
         }).handleAsync((avoid, throwable) -> {
             try {
                 TimeUnit.SECONDS.sleep(3);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             log.info("我是1号异常处理器");
             return null;
         }).handle((obj, throwable) -> {
             log.info("我是3号异常处理器");
             return null;
         }).handleAsync((avoid, throwable) -> {
             log.info("我是2号异常处理器");
             throw new RuntimeException("处理异常中的异常");
         }).exceptionally(throwable -> {
             log.error("我是4号异常处理器", throwable);
             return null;
         }).exceptionally(throwable -> {
             log.error("我是5号异常处理器", throwable);
             return null;
         });
         //
         future.join() ;
     }


    /**
     * complete() 完成一个计算，触发客户端的等待
     * completeExceptionally() 也可以抛出一个异常，触发客户端的等待
     *
     * 我们有两个后门方法可以重设这个值:obtrudeValue、obtrudeException
     * 但是使用的时候要小心，因为complete已经触发了客户端，有可能导致客户端会得到不期望的结果。
     */
     @Test
     public void test11(){
        CompletableFuture<String> f = new CompletableFuture<>() ;
         boolean flag1 = f.complete("100");
         log.info("flag1: {}", flag1);
         log.info("result : {}", f.join());
     }

    @Test
    public void test12(){
        CompletableFuture<String> f = new CompletableFuture<>() ;
        boolean flag2 = f.completeExceptionally(new Exception("自定义异常"));
        log.info("flag2: {}", flag2);
        log.info("result : {}", f.join());
    }

    // Java Future 转 CompletableFuture
    @Test
    public void test13(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> "hello world");
        CompletableFuture<String> cf = toCompletable(future, executorService);
    }

    // future -> CompletableFuture
    public static <T> CompletableFuture<T> toCompletable(Future<T> future, Executor executor){
         return CompletableFuture.supplyAsync(() -> {
             try {
                 return future.get() ;
             }catch (InterruptedException | ExecutionException e){
                 throw new RuntimeException(e) ;
             }
         }, executor) ;
    }


    @Test
    public void test14(){
        List<CompletableFuture<String>> futures = Lists.newArrayList(
                CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "a" ;
                }),
                CompletableFuture.supplyAsync(() ->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "b" ;
                }),
                CompletableFuture.supplyAsync(() ->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "c" ;
                }),
                CompletableFuture.supplyAsync(() ->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "d" ;
                })
        ) ;
        List<String> resultList = sequence(futures).join() ;
        log.info("result list : {}", resultList);
    }


    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures){
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList())) ;
    }

    public static <T> CompletableFuture<List<T>> sequence(Stream<CompletableFuture<T>> futures) {
        List<CompletableFuture<T>> futureList = futures.filter(Objects::nonNull).collect(Collectors.toList());
        return sequence(futureList);
    }

}
