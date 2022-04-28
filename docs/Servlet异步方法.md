1. 配置tomcat参数
    ```yml
    server:
      tomcat:
        max-connections: 1000  # 最大连接数，注意这个只要连接建立没有断开都计数（只要没有response结束，这个数值一直被占用,这个数值一般会大于max-threads参数）
        max-threads: 6         # tomcat核心线程数asyncContext.start(() ->{}) 会使用这里的线程，所以业务需要单独配置线程池 
        min-spare-threads: 2
        accept-count: 1000     # 等待队列大小
    ```
2. 编写异步业务代码
    ```java
    @RestController
    public class HelloController {
        @Autowired
        private Executor executorService ;
        @GetMapping("/hello/hold")
        public void hold2(HttpServletRequest request, HttpServletResponse response){
            log.info("请求进入.............");
            AsyncContext asyncContext = request.startAsync(request, response);
            executorService.execute(() -> {
                // 注意这里使用asyncContext.start会占用tomcat的max-threads参数配置的线程数
                //asyncContext.start(() -> {
                try {
                    log.info("异步执行线程:" + Thread.currentThread().getName());
                    String str = "hello world" ;
                    Thread.sleep(20000);
                    asyncContext.getResponse().setCharacterEncoding("UTF-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("这是【异步】的请求返回: " +str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //异步请求完成通知，所有任务完成了，才执行
                asyncContext.complete();
            });
            log.info("主线程返回.....");
        }
    
        @GetMapping("/hello/normal")
        public String helloNormal() throws InterruptedException {
            log.info("hello ...");
            log.info("mono execute ...");
            //Thread.sleep(3000);
            return "hello world" ;
        }
    }
    ```
3. 测试异步效果
    ```txt
    a. 同时启动6个线程访问/hello/hold接口
    b. 访问/hello/normal接口
    c. 查看/hello/normal返回结果
    ```