package com.yicj.func.handler;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-04-12 17:04
 **/
public class MHSub extends MHSuper{

    public static int y(Boolean a){
        System.out.println("sub::static");
        return 1 ;
    }

    public int x(boolean a){
        System.out.println("sub::primitive");
        return 1 ;
    }

    public int x(Boolean a){
        System.out.println("sub::boxed");
        return 1 ;
    }
}
