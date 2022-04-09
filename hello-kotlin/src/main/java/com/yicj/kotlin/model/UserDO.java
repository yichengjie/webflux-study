package com.yicj.kotlin.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDO implements Serializable {
    private String name ;
    private Integer age ;
}
