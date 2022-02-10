package com.yicj.reactor.model;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-10 10:04
 **/
public class ErrorCode {
    private String code ;
    private String msg ;

    public ErrorCode(String code, String msg) {
        this.code = code ;
        this.msg = msg ;
    }
}
