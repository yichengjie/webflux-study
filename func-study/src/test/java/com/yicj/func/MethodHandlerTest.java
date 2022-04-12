package com.yicj.func;

import com.yicj.func.handler.MHSub;
import com.yicj.func.handler.MHSuper;
import org.junit.Test;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-04-12 17:00
 **/
public class MethodHandlerTest {

    //https://my.oschina.net/u/3847203/blog/2221569
    @Test
    public void findVirtual() throws Throwable {
        Object a ;
        MethodType mt = MethodType.methodType(int.class, Boolean.class) ;
        MethodHandle mh = MethodHandles.lookup().findVirtual(MHSuper.class, "x", mt);
        //mh.bindTo(null).invoke(false) ; // NullPointerException
        //mh.invoke(false) ; //WrongMethodTypeException: cannot convert MethodHandle(MHSuper,Boolean)int to (boolean)void
        //mh.bindTo(new Object()).invoke(false) ; //Cannot cast java.lang.Object to com.yicj.func.handler.MHSuper
        //mh.bindTo(new MHSuper()).invoke(false); //super::boxed
        //mh.bindTo(new MHSuper()).invoke(Boolean.FALSE) ; //super::boxed
        ////////////////////////////////////////////////////////////////////
        //a = (int)mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //super::boxed
        //a = (Number)mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //WrongMethodTypeException: expected (Boolean)int but found (Boolean)Number
        //a = (Integer)mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //WrongMethodTypeException: expected (Boolean)int but found (Boolean)Integer
        //mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //expected (Boolean)int but found (Boolean)void
        ////////////////////////////////////////////////////////////////////
        //mh.bindTo(new MHSub()).invoke(false) ; //sub::boxed
        //mh.bindTo(new MHSub()).invoke(Boolean.FALSE) ;//sub::boxed
        //a = (int)mh.bindTo(new MHSub()).invokeExact(Boolean.FALSE) ; //sub::boxed
        ////////////////////////////////////////////////////////////////////
    }

    @Test
    public void findStatic() throws Throwable{
        Object a ;
        MethodType mt = MethodType.methodType(int.class, Boolean.class) ;
        MethodHandle mh = MethodHandles.lookup().findStatic(MHSuper.class, "y", mt) ;
        // mh.invoke(false) ; //super:: static
        //a = (int) mh.invokeExact(Boolean.FALSE) ; //super:: static
        ////////////////////////////////////////////////////////////////////
        mh = MethodHandles.lookup().findStatic(MHSuper.class, "z", MethodType.methodType(int.class, MHSuper.class)) ;
        MHSuper sup = new MHSub() ;
        a = (int)mh.invokeExact(sup) ; //class com.yicj.func.handler.MHSub

        MHSub sub = new MHSub() ;
        a = (int)mh.invokeExact(sub) ; //WrongMethodTypeException: expected (MHSuper)int but found (MHSub)int

    }

}
