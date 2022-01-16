package com.yicj.mysql.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.mysql.entity.UserInfo;
import com.yicj.mysql.mapper.UserMapper;
import com.yicj.mysql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {

    @Autowired
    private UserMapper userMapper ;

    @Override
    public List<UserInfo> findList(UserInfo userInfo) {
        log.info("UserService findList start ..");
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>() ;
        if (StringUtils.isNoneBlank(userInfo.getName())){
            queryWrapper.like("name", userInfo.getName()) ;
        }
        if (StringUtils.isNoneBlank(userInfo.getSex())){
            queryWrapper.eq("sex", userInfo.getSex()) ;
        }

        if (StringUtils.isNoneBlank(userInfo.getEmail())){
            queryWrapper.eq("email", userInfo.getEmail()) ;
        }
        List<UserInfo> userInfos = userMapper.selectList(queryWrapper);
        log.info("UserService findList end ..");
        return userInfos ;
    }

}