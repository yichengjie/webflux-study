package com.yicj.mapstruct.convertor;

import com.yicj.mapstruct.model.bo.UserBO;
import com.yicj.mapstruct.model.entity.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


@Slf4j
public class UserBOTest {

    @Test
    public void hello() {
        // 创建 UserDO 对象
        UserDO userDO = new UserDO()
                .setId(1)
                .setUsername("yudaoyuanma")
                .setPassword("buzhidao");
        // <X> 进行转换
        UserBO userBO = UserConvert.INSTANCE.convert(userDO);
        System.out.println(userBO.getId());
        System.out.println(userBO.getUsername());
        System.out.println(userBO.getPassword());
    }
}