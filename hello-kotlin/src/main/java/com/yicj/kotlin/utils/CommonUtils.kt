package com.yicj.kotlin.utils

import org.springframework.stereotype.Component

@Component
class CommonUtils {
    fun helloName(name: String): String{
        return "hello, $name" ;
    }
}