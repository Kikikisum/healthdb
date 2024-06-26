package com.example.healthdb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Slf4j
@MapperScan("com.example.healthdb.dao")
@EnableScheduling
@Controller
public class HealthdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthdbApplication.class, args);
    }

}
