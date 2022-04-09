package com.yicj.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.yicj.kotlin"])
open class SpringKotlinApplication

fun main(args: Array<String>){
    SpringApplication.run(SpringKotlinApplication::class.java, *args)
}



