package com.yicj.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;

@EnableAdminServer
@SpringBootApplication(exclude = ValidationAutoConfiguration.class)
public class MonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorAdminApplication.class, args) ;
        System.out.println("Admin监听启动成功!");
    }
}
