package com.yicj.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

//https://www.cnblogs.com/felordcn/archive/2020/07/29/13395095.html
@SpringBootApplication
@EnableR2dbcRepositories
public class R2dbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcApplication.class, args) ;
    }
}
