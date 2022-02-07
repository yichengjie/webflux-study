package com.yicj.reactor.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-02-07 17:40
 **/
@Data
public class UserInfo implements Serializable {
    private String username ;
    private Integer age ;
    private String address ;
}
