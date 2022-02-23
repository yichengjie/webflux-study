package com.yicj.cache.enums;


import com.yicj.cache.constants.SpringCacheNamesConstant;
import lombok.Getter;

/**
 * @description:
 * @author: yicj1
 * @create: 2022-02-23 11:03
 **/
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
