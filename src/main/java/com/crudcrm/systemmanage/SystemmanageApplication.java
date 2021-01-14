package com.crudcrm.systemmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.crudcrm.systemmanage.crm.mapper")
public class SystemmanageApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SystemmanageApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SystemmanageApplication.class);
    }
}
