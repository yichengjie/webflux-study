package com.yicj.hello.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private String id;

    private String username;

    private String password;
}
