package com.yicj.cache.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.yicj.cache.constants.SpringCacheNamesConstant;
import com.yicj.cache.model.UserInfo;
import com.yicj.cache.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @author: yicj1
 * @create: 2022-02-22 14:09
 **/
@Slf4j
@Service
@CacheConfig(cacheNames = {"texts"})
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name, String address) {
        return this.doHello(name, address) ;
    }

    @Override
    @Cacheable(value = "books", key = "'book-' + #name + ':' + #address", sync = true)
    public String doHello(String name, String address){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RandomUtil.randomString(20) ;
    }

    @Override
    @Cacheable(value = SpringCacheNamesConstant.USER_INFO_DETAIL_CACHE_NAMES/*, key = "'user_' + #id"*/ ,keyGenerator = "customKeyGenerator")
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

    @Override
    @Cacheable(value = "texts")
    public String query() {
        log.info("调用方法内部，未走缓存");
        return "text_query";
    }

    @Override
    @CachePut("texts")
    public String update() {
        return "text_update";
    }

    @Override
    @CacheEvict("texts")
    public String delete() {
        return "text_delete";
    }
}
