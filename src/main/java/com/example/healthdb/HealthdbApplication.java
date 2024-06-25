package com.example.healthdb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.example.healthdb.dao")
public class HealthdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthdbApplication.class, args);
    }

}
