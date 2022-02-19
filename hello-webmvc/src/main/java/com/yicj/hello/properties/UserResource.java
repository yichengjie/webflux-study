package com.yicj.hello.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Data
@Configuration
@ConfigurationProperties("com.yicj.opensource")
@PropertySource(value = "classpath:resource.properties")
public class UserResource {
    private String name ;
    private String website ;
    private String language ;
}
