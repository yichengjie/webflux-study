package com.yicj.feigin.api;

import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ProductAPITest {

    @Test
    public void detail(){
        ProductAPI productAPI = Feign.builder()
                .target(ProductAPI.class, "http://localhost:8080") ;
        // 调用获得商品
        String detail = productAPI.detail(1);
        log.info("product detail : {}" , detail);
    }

}
