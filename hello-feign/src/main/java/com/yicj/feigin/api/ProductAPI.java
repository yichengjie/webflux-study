package com.yicj.feigin.api;

import feign.Param;
import feign.RequestLine;

// 商品 API
public interface ProductAPI {

    // 获取商品详情
    @RequestLine("POST /product/detail/{id}")
    String detail(@Param("id") Integer id) ;
}
