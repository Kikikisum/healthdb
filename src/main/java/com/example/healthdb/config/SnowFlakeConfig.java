package com.example.healthdb.config;

import com.example.healthdb.utils.SnowFlakeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowFlakeConfig {

    @Bean
    public SnowFlakeUtils snowFlakeUtils()
    {
        return new SnowFlakeUtils(1L);
    }
}
