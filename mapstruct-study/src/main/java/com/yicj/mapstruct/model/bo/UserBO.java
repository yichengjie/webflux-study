package com.yicj.mapstruct.model.bo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data // 新增
@Accessors(chain = true)
public class UserBO {
    // 用户编号
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
}
