package com.yicj.study;

import org.junit.Test;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-22 09:57
 **/
public class ClassLoaderTest {

    @Test
    public void testClassLoader(){
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(this.getClass().getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
    }
}
