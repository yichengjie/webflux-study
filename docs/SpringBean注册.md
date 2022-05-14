#### 使用BeanDefinitionReaderUtils#registerBeanDefinition注册
1. 编写简单实体
```java
@Data
public class UserInfo implements Serializable {
    private String id ;
    private String userCode ;
}
```
2. 编写单元测试
```java
public class BeanDefinitionReaderUtilsTest {
    @Autowired
    private ApplicationContext applicationContext ;
    @Test
    public void registerBeanDefinition(){
        String className = UserInfo.class.getName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(UserInfo.class);
        definition.addPropertyValue("id", "10011");
        definition.addPropertyValue("userCode", "yicj1");
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        String alias =  "userInfoVoBean";
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
```

