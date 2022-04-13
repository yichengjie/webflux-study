package com.yicj.func;

import com.yicj.func.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-04-13 15:57
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuncApplication.class)
public class BeanDefinitionReaderUtilsTest {
    @Autowired
    private ApplicationContext applicationContext ;
    @Test
    public void registerBeanDefinition(){
        String className = UserInfo.class.getName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(UserInfo.class);
        definition.addPropertyValue("id", "10011");
        definition.addPropertyValue("userCode", "15712867682");
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        String alias =  "userInfoVoFeignClient";
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, className);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className,
                new String[] { alias });
        // 这里BeanDefinitionHolder注意与BeanWrapperImpl的区分
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, (BeanDefinitionRegistry)applicationContext);
        Object bean = applicationContext.getBean(className);
        log.info("===> bean info : {}", bean); //UserInfo(id=10011, userCode=15712867682)
    }
}
