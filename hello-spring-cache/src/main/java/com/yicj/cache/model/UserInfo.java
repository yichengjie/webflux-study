package com.yicj.cache.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @author: yicj1
 * @create: 2022-02-23 09:56
 **/
@Data
public class UserInfo implements Serializable {
    private String id;
    private String name ;
    private String address ;
    private Integer age ;
    private LocalDateTime birthday ;
    private Date createDate ;
}
