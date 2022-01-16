package com.yicj.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;

@Data
@Table("user")
public class UserInfo implements Serializable {
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