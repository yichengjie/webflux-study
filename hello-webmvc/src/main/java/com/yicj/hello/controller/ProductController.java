package com.yicj.hello.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @PostMapping("/product/detail/{id}")
    public String detail(@PathVariable("id") Integer id){
        return "product detail with id : " + id ;
    }

}
