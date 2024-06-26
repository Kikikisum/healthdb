package com.example.healthdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://47.92.115.49:9001")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")  // 允许所有的请求头
                .exposedHeaders("Content-Disposition")  // 暴露特定的头部，如有需要可以添加其他头部
                .allowCredentials(true)
                .maxAge(3600);
    }
}
