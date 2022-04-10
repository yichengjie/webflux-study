package com.yicj.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.yicj.kotlin"])
open class SpringKotlinApplication

//mvn clean kotlin:compile package -Dmaven.test.skip=true
//mvn clean kotlin:compile package '-Dmaven.test.skip=true'
//https://www.jianshu.com/p/d3b4b60aecd2
fun main(args: Array<String>){
    SpringApplication.run(SpringKotlinApplication::class.java, *args)
}