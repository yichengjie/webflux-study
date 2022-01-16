package com.yicj.mysql.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yicj.mysql.entity.UserInfo;

import java.util.List;

public interface UserService extends IService<UserInfo> {

    List<UserInfo> findList(UserInfo userInfo) ;
}
