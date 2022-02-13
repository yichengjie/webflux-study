package com.yicj.study.model;

import lombok.Data;

@Data
public class Pair<T,U>{
    private T left ;
    private U right ;

    public  static <T,U> Pair<T,U> of(T left, U right){
        Pair<T,U> pair = new Pair<>();
        pair.setLeft(left);
        pair.setRight(right);
        return pair ;
    }
}