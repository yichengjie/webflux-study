package com.yicj.future.util;

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-26 15:35
 **/
public class CompletableFutureTimeout {

    public static <T>CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit){
        CompletableFuture<T> result = new CompletableFuture<>() ;
        // timeout时间后，抛出TimeoutException 类似与sentinel / watcher
        CompletableFutureTimeout.Delayer.delayer.schedule(() ->{
            // 使用obtrudeException或则completeExceptionally都可以达到相同效果
            //result.obtrudeException(new TimeoutException()) ;
            return result.completeExceptionally(new TimeoutException()) ;
        } , timeout, unit) ;
        return result ;
    }

    /**
     * 哪个先完成 就apply哪一个结果 这是一个关键的API,exceptionally出现异常后返回默认值
     * @param t
     * @param future
     * @param timeout
     * @param unit
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> completeOnTimeout(T t, CompletableFuture<T> future, long timeout, TimeUnit unit) {
        final CompletableFuture<T> timeoutFuture = timeoutAfter(timeout, unit);
        return future.applyToEither(timeoutFuture, Function.identity()).exceptionally((throwable) -> t);
    }

    /**
     * 哪个先完成 就apply哪一个结果 这是一个关键的API，不设置默认值，超时后抛出异常
     * @param t
     * @param future
     * @param timeout
     * @param unit
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> orTimeout(T t, CompletableFuture<T> future, long timeout, TimeUnit unit) {
        final CompletableFuture<T> timeoutFuture = timeoutAfter(timeout, unit);
        return future.applyToEither(timeoutFuture, Function.identity()).exceptionally((throwable) -> t);
    }



    static final class Delayer{
        static final ScheduledThreadPoolExecutor delayer ;

        static ScheduledFuture<?> delay(Runnable command, long delay, TimeUnit unit){
            return delayer.schedule(command, delay, unit) ;
        }

        // 注意，这里使用一个线程就可以搞定 因为这个线程并不真的执行请求 而是仅仅抛出一个异常
        static {
            delayer = new ScheduledThreadPoolExecutor(1, new CompletableFutureTimeout.Delayer.DaemonThreadFactory()) ;
            delayer.setRemoveOnCancelPolicy(true);
        }

        static final class DaemonThreadFactory implements ThreadFactory{
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable) ;
                thread.setDaemon(true);
                thread.setName("CompletableFutureDelayScheduler");
                return thread;
            }
        }
    }
}
