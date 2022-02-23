package com.yicj.cache.config;

import com.yicj.cache.enums.SpringCacheRedis;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-23 13:15
 **/
@Configuration
public class SpringCacheConfig {
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

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }

    static class CustomKeyGenerator implements KeyGenerator {
        public Object generate(Object target, Method method, Object... params) {
            return target.getClass().getSimpleName() + ":"
                    + method.getName() + ":"
                    + StringUtils.arrayToDelimitedString(params, "_");
        }
    }
}
