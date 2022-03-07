package com.yicj.mapstruct.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-03-07 14:11
 **/
@Data
@Accessors(chain = true)
public class UserDetailBO {

    // 用户编号
    private Integer userId;
    // 用户名
    private String username;
    // 密码
    private String password;
}
