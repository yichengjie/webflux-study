package com.yicj.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;

@Data
@Table("user")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -5644799954031156649L;
    //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
    @Id
    private Integer id;
    @Column("name")
    private String name;
    @Column("sex")
    private String sex;
    @Column("pwd")
    private String pwd;
    @Column("email")
    private String email;
}