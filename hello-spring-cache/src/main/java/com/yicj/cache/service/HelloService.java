package com.yicj.cache.service;


import com.yicj.cache.model.UserInfo;

/**
 * @description:
 * @author: yicj1
 * @create: 2022-02-22 14:08
 **/
public interface HelloService {

    String hello(String name, String address) ;

    String doHello(String name, String address) ;

    UserInfo findById(String id) ;

    String query() ;

    String update() ;

    String delete() ;
}
