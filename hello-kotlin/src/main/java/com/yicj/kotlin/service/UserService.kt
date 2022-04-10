package com.yicj.kotlin.service

import com.yicj.kotlin.model.UserDO
import com.yicj.kotlin.model.UserVO

interface UserService {
    fun saveUser(user: UserDO): UserVO
}