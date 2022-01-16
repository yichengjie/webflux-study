package com.yicj.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.mysql.entity.UserInfo;
import com.yicj.mysql.mapper.UserMapper;
import com.yicj.mysql.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {

}