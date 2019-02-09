package com.warehouse.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@MapperScan(basePackages = "com.warehouse.manage.dao")
public class ManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }
}
