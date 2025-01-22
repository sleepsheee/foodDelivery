package com.fd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
public class FdApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdApplication.class, args);
        log.info("server started");
    }
}
