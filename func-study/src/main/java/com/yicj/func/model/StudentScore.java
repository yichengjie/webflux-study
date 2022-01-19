package com.yicj.func.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-19 08:45
 **/
@Data
public class StudentScore implements Serializable {
    private String stuName ;
    private String subject ;
    private Integer score ;
}
