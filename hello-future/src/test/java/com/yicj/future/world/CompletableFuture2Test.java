package com.yicj.future.world;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

//https://blog.csdn.net/qq_31865983/article/details/106137777
@Slf4j
public class CompletableFuture2Test {

    // 创建异步任务，有返回值
    @Test
    public void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start time --> {}", System.currentTimeMillis());
            sleep();
            if (true) {
                throw new RuntimeException("test");
            } else {
                log.info("exit,time --> {}", System.currentTimeMillis());
                return 1.2;
            }
        });
        log.info("main thread start, time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("run result -> {}", cf.get());
        log.info("main thread exit, time: {}", System.currentTimeMillis());
    }

    // 创建异步执行任务，无返回值
    @Test
    public void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            log.info("start, time -> {}", System.currentTimeMillis());
            sleep();
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info("exit, time -> {}", System.currentTimeMillis());
            }
        });
        log.info("main thread start, time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("run result -> {}", cf.get());
        log.info("main thread exit, time : {}", System.currentTimeMillis());
    }

    // 异步回调
    @Test
    public void thenApply() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool() ;
        // 创建异步执行任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        }, pool);
        //cf关联得异步任务得返回值作为方法入参，传入到thenApply的方法中
        // thenApply这里实际创建了一个新的CompletableFuture实例
        CompletableFuture<String> cf2 = cf.thenApply(result -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return "test: " + result;
        });
        //
        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("run result -> {}", cf.get());
        log.info("main thread start cf2.get(), time -> {}", System.currentTimeMillis());
        log.info("run result -> {}", cf2.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void thenAccept() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool() ;
        // 创建异步执行任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        },pool);
        // cf关闭的异步任务的返回值作为方法入参，传入到thenApply的方法中
        CompletableFuture<Void> cf2 = cf.thenApply(result -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return "test: " + result;
        }).thenAccept(result -> {
            log.info("start jbo3, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job3, time -> {}", System.currentTimeMillis());
        }).thenRun(() -> {
            log.info("start job4, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("thenRun do something .");
            log.info("exit job4, time -> {}", System.currentTimeMillis());
        });
        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子线程执行完成
        log.info("run result -> {}", cf.get());
        log.info("main thread start cf2.get(), time -> {}", System.currentTimeMillis());
        // cf2 等待最后一个thenRun执行完成
        log.info("run result -> {}", cf2.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void exceptionally() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool() ;
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("job1 start time -> {}", System.currentTimeMillis());
            sleep();
            if (true) {
                throw new RuntimeException("test");
            } else {
                log.info("job1 exit, time -> {}", System.currentTimeMillis());
                return 1.2;
            }
        }, pool);
        // cf 执行异常时，将抛出的异常作为入参传递给回调方法
        CompletableFuture<Double> cf2 = cf.exceptionally(error -> {
            log.info("start, time -> {}", System.currentTimeMillis());
            sleep();
            log.error("error stack trace ->", error);
            log.info("exit, time -> {}", System.currentTimeMillis());
            return -1.1;
        });
        // cf正常执行时的逻辑，如果执行异常则不调用此逻辑
        CompletableFuture<Void> cf3 = cf.thenAccept(param -> {
            log.info("job2 start, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("param -> {}", param);
            log.info("job2 exit, time -> {}", System.currentTimeMillis());
        });
        log.info("main thread start, time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成，此处无论是job2还是job3都实现job2退出，主线程才退出，
        // 如果是cf,则主线程不会等待job2执行完成自动退出
        //cf2.get时，没有异常，但是依然有返回值，就是cf的返回值
        log.info("run result -> {}", cf2.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void whenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("job1 start, time -> {}", System.currentTimeMillis());
            sleep();
            if (false) {
                throw new RuntimeException("test");
            } else {
                log.info("job1 exit, time -> {}", System.currentTimeMillis());
                return 1.2;
            }
        });
        //cf执行完成后会将执行结果和执行过程中抛出的异常传入回调方法，如果是正常执行的则传入的异常为null
        CompletableFuture<Double> cf2 = cf.whenComplete((result, error) -> {
            log.info("job2 start, time -> {}", System.currentTimeMillis());
            sleep();
            if (error != null) {
                log.error("error stack trace -> ", error);
            } else {
                log.info("run success, result -> {}", result);
            }
            log.info("job2 exit, time -> {}", System.currentTimeMillis());
        });
        // 等待子任务执行完成
        log.info("main thread start wait, time -> {}", System.currentTimeMillis());
        // 如果cf是正常执行的，cf2.get的结果就是cf执行结果
        // 如果cf是执行异常，则cf2.get会抛出异常
        log.info("run result -> {}", cf2.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void handle() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("job1 start, time -> {}", System.currentTimeMillis());
            sleep();
            if (true) {
                throw new RuntimeException("test");
            } else {
                log.info("job1 exit, time -> {}", System.currentTimeMillis());
                return 1.2;
            }
        });
        // cf执行完成后会将执行结果和执行过程中抛出的异常传入回调方法，如果是正常执行的则传入的异常为null
        CompletableFuture<String> cf2 = cf.handle((result, error) -> {
            log.info("job2 start, time -> {}", System.currentTimeMillis());
            sleep();
            if (error != null) {
                log.error("error stack trace -> ", error);
            } else {
                log.info("run success, result -> {}", result);
            }
            log.info("job2 exit, time -> {}", System.currentTimeMillis());
            if (error != null) {
                return "run error";
            } else {
                return "run success";
            }
        });
        // 等待子任务执行完成
        log.info("main thread start wait, time -> {}", System.currentTimeMillis());
        // get的结果是cf2的返回值，跟cf没有关系了
        log.info("run result -> {}", cf2.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void thenCombine() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool() ;
        // 创建异步任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });
        //
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return 3.2;
        });
        // cf和cf2的异步任务都执行完成后，会将执行结果作为方法入参传递给cf3，且有返回值
        CompletableFuture<Double> cf3 = cf.thenCombine(cf2, (a, b) -> {
            log.info("start job3, time -> {}", System.currentTimeMillis());
            log.info("job3 param a -> {}, b -> {}", a, b);
            sleep();
            log.info("exit job3, time -> {}", System.currentTimeMillis());
            return a + b;
        });

        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("cf5 run result -> {}", cf3.get());
    }

    @Test
    public void thenAcceptBoth() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool() ;
        // 创建异步任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });
        //
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return 3.2;
        });
        // cf和cf2的异步任务都执行完成后，会将其执行结果作为方法入参传递给cf3,无返回值
        CompletableFuture<Void> cf4 = cf.thenAcceptBoth(cf2, (a, b) -> {
            log.info("start job4, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job4, time -> {}", System.currentTimeMillis());
        });
        // cf4和cf3 都执行完成后，执行cf5,五入参，无返回值
        // cf4和cf3 都执行完成后，执行cf5,五入参，无返回值
        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("cf5 run result -> {}", cf4.get());
    }

    @Test
    public void runAfterBoth() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool() ;
        // 创建异步任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });
        //
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return 3.2;
        });
        // cf和cf2的异步任务都执行完成后，会将其执行结果作为方法入参传递给cf3,无返回值
        CompletableFuture<Void> cf5 = cf.runAfterBoth(cf2, () -> {
            log.info("start job5, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job5, time -> {}", System.currentTimeMillis());
        });
        // cf4和cf3 都执行完成后，执行cf5,五入参，无返回值
        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("cf5 run result -> {}", cf5.get());
    }

    private void sleep(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
