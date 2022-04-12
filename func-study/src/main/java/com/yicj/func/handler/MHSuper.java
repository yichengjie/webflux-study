package com.yicj.func.handler;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-04-12 17:01
 **/
public class MHSuper {

    public static int y(Boolean a){
        System.out.println("super:: static");
        return 1 ;
    }

    public static int z(MHSuper a){
        System.out.println(a.getClass());
        return 1 ;
    }

    public int x(boolean a){
        System.out.println("super::primitive");
        return 1 ;
    }

    public int x(Boolean a){
        System.out.println("super::boxed");
        return 1 ;
    }
}
