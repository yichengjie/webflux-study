1. 项目映入依赖
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
    </dependencies>
    ```
2. yml添加配置
    ```yml
    spring.cache.type: redis
    ```
3. 配置cacheManager支持过期时间
    ```java
    @Configuration
    public class RedisConfig {
        @Bean(name = "customRedisTemplate")
        public RedisTemplate<String, Object> customRedisTemplate(RedisConnectionFactory factory) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // 解决jackson2无法反序列化LocalDateTime的问题
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    
            /////////////////////////////////////////////////////////////////////////
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            RedisSerializer<String> stringSerializer = new StringRedisSerializer();
            template.setConnectionFactory(factory);
            template.afterPropertiesSet();
            // string
            template.setKeySerializer(stringSerializer);
            template.setValueSerializer(jackson2JsonRedisSerializer);
            // hash
            template.setHashKeySerializer(stringSerializer);
            template.setHashValueSerializer(stringSerializer);
            // 更新redisTemplate属性配置
            template.afterPropertiesSet();
            return template;
        }
        @Bean
        public CacheManager cacheManager(RedisTemplate<String,Object> template){
            // 构造redis缓存管理器
            RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
                    // redis链接工厂
                    .fromConnectionFactory(template.getConnectionFactory())
                    // 缓存配置
                    .cacheDefaults(getCacheConfigurationWithTtl(template, 60 * 60))
                    .withCacheConfiguration(SpringCacheRedis.USER_INFO_DETAIL.getCacheNames(), getCacheConfigurationWithTtl(template, SpringCacheRedis.USER_INFO_DETAIL.getExpireSeconds()))
                    .withCacheConfiguration("cache_post", getCacheConfigurationWithTtl(template, 120))
                    //配置同步修改或则删除put/evict
                    .transactionAware()
                    .build() ;
            return redisCacheManager ;
        }
        private RedisCacheConfiguration getCacheConfigurationWithTtl(RedisTemplate<String, Object> template, long seconds) {
            return RedisCacheConfiguration
                    .defaultCacheConfig()
                    // 设置key为String
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
                    // 设置value 为自动转Json的Object
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
                    // 不缓存null
                    .disableCachingNullValues()
                    // 缓存数据保存1小时
                    .entryTtl(Duration.ofSeconds(seconds));
        }
    }
    ```
4. 常量添加
    ```text
    public interface SpringCacheNamesConstant {
        String USER_INFO_DETAIL_CACHE_NAMES = "springcache:userservice:userinfo:detail" ;
    }
    ```
5. 枚举添加
    ```text
    @Getter
    public enum SpringCacheRedis {
        USER_INFO_DETAIL(SpringCacheNamesConstant.USER_INFO_DETAIL_CACHE_NAMES, 60L, "用户详情") ;
    
        // 缓存名称
        private final String cacheNames ;
        // 过期秒数
        private final Long expireSeconds ;
        // 描述信息
        private final String msg ;
    
        SpringCacheRedis(String cacheNames, Long expireSeconds, String msg){
            this.cacheNames = cacheNames ;
            this.expireSeconds = expireSeconds ;
            this.msg  = msg ;
        }
    }
    ```
6. 业务代码编写
    ```text
    @Service
    @CacheConfig(cacheNames = {"texts"})
    public class HelloServiceImpl implements HelloService {
        @Override
        @Cacheable(value = SpringCacheNamesConstant.USER_INFO_DETAIL_CACHE_NAMES, key = "'user_' + #id ")
        public UserInfo findById(String id) {
            UserInfo userInfo = new UserInfo() ;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userInfo.setId(id);
            userInfo.setId(id);
            userInfo.setName("张三");
            userInfo.setAddress("北京");
            userInfo.setAge(20);
            userInfo.setBirthday(LocalDateTime.now());
            userInfo.setCreateDate(new Date());
            return userInfo;
        }
    }
    ```
7. 其他提示
   ```text
   a. RedisTemplate配置中LocalDateTime支持
   b. CacheManager.withCacheConfiguration配置中，缓存的有效时间需要配置，否则使用默认的1小时
   c. 将缓存名称定义到常量SpringCacheNamesConstant中（注解Cacheable中无法读取枚举的字段值）
   d. 将缓存名与过期时间定义到SpringCacheRedis中 (在CacheManager配置、业务缓存地方会使用)
   ```