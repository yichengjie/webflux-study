package com.yicj.future.world;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

//https://blog.csdn.net/qq_31865983/article/details/106137777
//https://blog.csdn.net/winterking3/article/details/116026768
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

    //thenApply（）转换的是泛型中的类型，相当于将CompletableFuture<T> 转换生成新的CompletableFuture<U>
    //thenCompose（）用来连接两个CompletableFuture，是生成一个新的CompletableFuture
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
    public void thenApplyNeedReturnCompletionStageTest() {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "---supplyAsync method -> ") ;
        CompletableFuture<CompletableFuture<String>> cf2 = cf.thenApply((s) -> CompletableFuture.supplyAsync(() -> s + "---theApply method -> "));
        cf2.thenAccept((c) -> c.thenAccept((f) -> System.out.println(f +  "---thenAccept")));
        // ForkJoinPool.commonPool-worker-9---supplyAsync method ->
        // ForkJoinPool.commonPool-worker-9---theApply method -> main---thenAccept
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

    /**
     * thenCombine 会在两个任务都执行完成后，把两个任务的结果合并。
     * 注意：
     * 两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
     * 两个任务是并行执行的，它们之间并没有先后依赖顺序。
     */
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


    @Test
    public void applyToEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep(2001);
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });

        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return 3.2;
        });
        // cf和cf2的异步任务都执行完成后，会将执行结果作为方法入参传递给cf3，且有返回值
        CompletableFuture<Double> cf3 = cf.applyToEither(cf2, result -> {
            log.info("start job3, time -> {}", System.currentTimeMillis());
            log.info("job2 param result -> {}", result);
            sleep();
            log.info("exit job3, time -> {}", System.currentTimeMillis());
            return result;
        });

        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("cf run result -> {}",cf.get());
        log.info("main thread start cf3.get(), time -> {}", System.currentTimeMillis());
        log.info("cf3 run result -> {}", cf3.get());
        log.info("main thread exit exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void acceptEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });

        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return 3.2;
        });
        // cf和cf2的异步任务都执行完成后，会将执行结果作为方法入参传递给cf3，且有返回值
        CompletableFuture<Void> cf3 = cf.acceptEither(cf2, result -> {
            log.info("start job3, time -> {}", System.currentTimeMillis());
            log.info("job2 param result -> {}", result);
            sleep();
            log.info("exit job3, time -> {}", System.currentTimeMillis());
        });

        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("cf run result -> {}",cf.get());
        log.info("main thread start cf3.get(), time -> {}", System.currentTimeMillis());
        log.info("cf3 run result -> {}", cf3.get());
        log.info("main thread exit exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void runAfterEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep(2001);
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });

        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return 3.2;
        });
        // cf和cf2的异步任务都执行完成后，会将执行结果作为方法入参传递给cf3，且有返回值
        CompletableFuture<Void> cf3 = cf.runAfterEither(cf2, () -> {
            log.info("start job3, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job3, time -> {}", System.currentTimeMillis());
        });
        //
        log.info("main thread start cf.get(), time -> {}", System.currentTimeMillis());
        // 等待子任务执行完成
        log.info("cf run result -> {}",cf.get());
        log.info("main thread start cf3.get(), time -> {}", System.currentTimeMillis());
        log.info("cf3 run result -> {}", cf3.get());
        log.info("main thread exit exit, time -> {}", System.currentTimeMillis());
    }

    /**
     * thenCompose 可以用于组合多个CompletableFuture，将前一个任务的返回结果作为下一个任务的参数，它们之间存在着业务逻辑上的先后顺序。
     * thenCompose方法会在某个任务执行完成后，将该任务的执行结果作为方法入参然后执行指定的方法，该方法会返回一个新的CompletableFuture实例。
     */
    @Test
    public void thenCompose(){
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });

        cf.thenCompose(param -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return CompletableFuture.supplyAsync(() -> {
                log.info("start job3, time, time -> {}", System.currentTimeMillis());
                sleep();
                log.info("exit job3, time -> {}", System.currentTimeMillis());
                return "job3 test" ;
            }) ;
        }) ;

    }

    @Test
    public void allOf() throws ExecutionException, InterruptedException {
        // 创建异步任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });

        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep(1500);
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            return 3.2;
        });

        CompletableFuture<Double> cf3 = CompletableFuture.supplyAsync(() -> {
            log.info("start job3, time -> {}", System.currentTimeMillis());
            sleep(1300);
            log.info("exit job3, time -> {}", System.currentTimeMillis());
            // throw new RuntimeException("test") ;
            return 2.2;
        });
        // allOf等待所有任务执行完成才执行cf4，如果有一个任务异常终止，则cf4.get时会抛出异常，都是正常运行，cf4返回null
        // anyOf是只有一个任务执行完成，无论是正常或者异常，都会执行cf4,cf4.get的结果就是已执行完成的任务的执行结果

        CompletableFuture<Void> cf4 = CompletableFuture.allOf(cf, cf2, cf3).whenComplete((result, error) -> {
            if (error != null) {
                log.info("error stack trace -> ", error);
            } else {
                log.info("run success, result -> {}", result);
            }
        });

        log.info("main thread start cf4.get(), time -> {}", System.currentTimeMillis());
        log.info("cf4 run result -> {}", cf4.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }

    @Test
    public void anyOf() throws ExecutionException, InterruptedException {
        // 创建异步任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("start job1, time -> {}", System.currentTimeMillis());
            sleep();
            log.info("exit job1, time -> {}", System.currentTimeMillis());
            return 1.2;
        });

        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            log.info("start job2, time -> {}", System.currentTimeMillis());
            sleep(1500);
            log.info("exit job2, time -> {}", System.currentTimeMillis());
            //throw new RuntimeException("test") ;
            return 3.2;
        });

        CompletableFuture<Double> cf3 = CompletableFuture.supplyAsync(() -> {
            log.info("start job3, time -> {}", System.currentTimeMillis());
            sleep(1300);
            log.info("exit job3, time -> {}", System.currentTimeMillis());
            // throw new RuntimeException("test") ;
            return 2.2;
        });
        // allOf等待所有任务执行完成才执行cf4，如果有一个任务异常终止，则cf4.get时会抛出异常，都是正常运行，cf4返回null
        // anyOf是只有一个任务执行完成，无论是正常或者异常，都会执行cf4,cf4.get的结果就是已执行完成的任务的执行结果

        CompletableFuture<Object> cf4 = CompletableFuture.anyOf(cf, cf2, cf3).whenComplete((result, error) -> {
            if (error != null) {
                log.info("error stack trace -> ", error);
            } else {
                log.info("run success, result -> {}", result);
            }
        });

        log.info("main thread start cf4.get(), time -> {}", System.currentTimeMillis());
        log.info("cf4 run result -> {}", cf4.get());
        log.info("main thread exit, time -> {}", System.currentTimeMillis());
    }


    private void sleep(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private void sleep(int num){
        try {
            TimeUnit.MILLISECONDS.sleep(num);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
