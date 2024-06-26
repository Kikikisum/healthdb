package com.example.healthdb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@MapperScan("com.example.healthdb.dao")
@EnableScheduling
public class HealthdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthdbApplication.class, args);
    }

}
