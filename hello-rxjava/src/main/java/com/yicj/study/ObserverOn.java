package com.yicj.study;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ObserverOn <T> extends Observable<T> {

    // 这个指向上游传入的Observable
    private Observable observable ;
    private Scheduler schedulers ;
    // 初始化接受上游传入的Observable实例，和线程调度类

    public ObserverOn(Observable observable, Scheduler scheduler){
        this.observable = observable ;
        this.schedulers = scheduler ;
    }

    // 当真正的订阅发生时，这个方法会被调用，传入下游的Observer
    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        // 先获得一个线程操作类，Work封装了具体的线程
        Scheduler.Worker worker = schedulers.createWorker() ;
        // 创建一个内部类
        ObserverOnInner inner = new ObserverOnInner(observer, worker) ;
        // 将这个内部类传递给上游
        // 上游Observable获取内部类的引用
        // 当上游有数据时，会将数据传递给这个内部类
        observable.subscribe(inner) ;
    }

    static final class ObserverOnInner<T> implements Observer<T>, Runnable, Disposable {

        // 持有下游observer
        private Observer<T> actual ;
        // 切换的线程
        private Scheduler.Worker curWork ;
        // 缓存的队列
        private Queue<T> cache ;
        private boolean hasCalled = false ;
        private boolean canceled = false;
        private boolean done = false ;
        private Throwable error ;

        public ObserverOnInner(Observer<T> observer, Scheduler.Worker worker) {
            this.curWork = worker ;
            this.actual = observer ;
            cache = new ConcurrentLinkedDeque<>() ;
        }

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            actual.onSubscribe(this);
        }

        // 上游Observable有数据时，会传入数据进来
        @Override
        public void onNext(@NonNull T value) {
            // 因为需要切换线程，因此暂时把数据缓存起来
            cache.offer(value) ;
            // 调起新线程
            schedule() ;
        }

        @Override
        public void onError(@NonNull Throwable e) {
            // 上游发生错误时处理
            error = e ;
            done = true ;
            schedule();
        }

        @Override
        public void onComplete() {
            // 完成时的处理
            done = true ;
            schedule();
        }

        @Override
        public void dispose() {
            canceled = true ;
        }

        @Override
        public boolean isDisposed() {
            return false;
        }

        @Override
        public void run() {
            // 此时，代码已经在新的线程环境中执行了
            // 开始一个死循环，不停从队列中取数据
            for (;;){
                if (canceled){
                    return;
                }
                // onError, onComplete()方法的处理
                if (done){
                    if (error != null){
                        actual.onError(error);
                        return;
                    }
                    actual.onComplete();
                    return;
                }
                // 从队列中获取数据
                T t = cache.poll() ;
                if (t != null){
                    // 将数据传递给下游的Observer
                    actual.onNext(t);
                }
            }
        }

        private void schedule() {
            // 这个方法只需要调用一次，因为只需要拉起一次线程
            if (!hasCalled){
                hasCalled = true ;
                // curWork背后的线程会执行这个runnable，调用run方法
                curWork.schedule(this) ;
            }
        }
    }
}
