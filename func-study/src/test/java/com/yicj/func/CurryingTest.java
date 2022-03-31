package com.yicj.func;

import org.junit.Test;

import java.util.function.Function;

/**
 * @program: bp-organization-center
 * @description:
 * @author: yicj1
 * @create: 2022-03-31 11:52
 **/
public class CurryingTest {

    @Test
    public void test1(){
        Sum sum = new Sum() ;
        //integerFlux.reduce()
        Func<Integer, Func<Integer, Integer>> func2 =  (a) ->{
            return (b) ->{
                return a + b ;
            } ;
        } ;
        Function<Integer, Function<Integer, Integer>> fun = (x) -> {
            return (y) -> {
                return x + y ;
            } ;
        } ;
        System.out.println(fun.apply(2).apply(3));	// 5
        Func<Integer, Func<Integer,Integer>> func = a -> {
            return b -> {
                return sum.sum(a,b) ;
            } ;
        } ;
        Integer exec = func.exec(1).exec(2);
        System.out.println(exec);
    }


    interface Func<T,R>{
        R exec(T a) ;
    }

    class Sum{
        public Integer sum(int a, int b){
            return a + b ;
        }
    }
}
