package com.yicj.mapstruct.convertor;

import com.yicj.mapstruct.model.bo.UserBO;
import com.yicj.mapstruct.model.bo.UserDetailBO;
import com.yicj.mapstruct.model.entity.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper // <1>=
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class); // <2>

    UserBO convert(UserDO userDO);

    @Mappings({
            @Mapping(source = "id", target = "userId")
    })
    UserDetailBO convertDetail(UserDO userDO) ;
}