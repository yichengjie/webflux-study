package com.yicj.mapstruct.model.bo;


//@Data
public class UserBO {

    /** 用户编号 **/
    private Integer id;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;
    // ... 省略 setter/getter 方法


    public Integer getId() {
        return id;
    }

    public UserBO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserBO setUsername(String username) {
        this.username = username;
        return this ;
    }

    public String getPassword() {
        return password;
    }

    public UserBO setPassword(String password) {
        this.password = password;
        return this ;
    }
}
