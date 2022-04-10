package com.yicj.kotlin.service.impl

import com.yicj.kotlin.model.UserDO
import com.yicj.kotlin.model.UserVO
import com.yicj.kotlin.service.HelloService
import com.yicj.kotlin.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {
    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)
    @Autowired
    lateinit var helloService: HelloService

    override fun saveUser(user: UserDO):UserVO {
        helloService.hello(user.name)
        log.info("保存用户: {} 的信息", user)
        var vo = UserVO()
        vo.name = user.name
        vo.age = user.age
        return vo
    }
}