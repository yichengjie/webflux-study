1. Feature是接口，可以通过线程池提交直接返回
    ```java
    @Slf4j
    public class FeatureTaskTest {
        @Test
        public void hello() throws ExecutionException, InterruptedException {
            ExecutorService pool = Executors.newFixedThreadPool(3);
            Runnable runnable = () -> {
                log.info("start .....");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("end .....");
            } ;
            Future<?> submit = pool.submit(runnable);
            Object o = submit.get();
            log.info("result : {} ", o);
        } 
    }
    ```
2. FeatureTask是实现类，可以直接提交给Thread获取Runnable中的结果
    ```java
    @Slf4j
    public class FeatureTaskTest {
        @Test
        public void hello2() throws ExecutionException, InterruptedException {
            Runnable runnable = () -> {
                log.info("start .....");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("end .....");
            } ;
            FutureTask<Void> futureTask = new FutureTask<Void>(runnable, null) ;
            Thread thread = new Thread(futureTask);
            thread.start();
            Object o = futureTask.get();
            log.info("result : {} ", o);
        }
    }
```
3. 在SimpleAsyncTaskExecutor中有使用FutureTask