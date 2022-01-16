package com.yicj.r2dbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "user")//指定表名
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -5644799954031156649L;
    //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
    @TableId(value = "id", type = IdType.AUTO)//指定自增策略
    private Integer id;
    private String name;
    private String sex;
    private String pwd;
    private String email;
}