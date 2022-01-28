package com.yicj.future.util;

import com.yicj.future.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BeanUtilsTest {

    @Test
    public void copyProperties(){
        UserInfo org = new UserInfo();
        org.setUsername("张三");
        org.setAge(20);
        org.setAddress("BJS");
        UserInfo dest = new UserInfo() ;
        BeanUtils.copyProperties(org, dest, "age");
        log.info("dest : {}", dest);
    }

    @Test
    public void test2(){
        UserInfo org = new UserInfo();
        org.setUsername("张三");
        //org.setAge(20);
        //org.setAddress("BJS");
        BeanWrapper wrapper = new BeanWrapperImpl(org) ;
        PropertyDescriptor[] propertyDescriptors = wrapper.getPropertyDescriptors();
        List<String> nameList = Stream.of(propertyDescriptors)
                .filter(pd -> {
                    String name = pd.getName();
                    Object value = wrapper.getPropertyValue(name);
                    log.info("name : {}, value: {}", name, value);
                    return value == null;
                })
                .map(PropertyDescriptor::getName)
                .collect(Collectors.toList());
        log.info("nameList : {}", nameList);
        //
        String[] strings = nameList.toArray(new String[0]);
        log.info("array : {}",strings.length );
    }
}
