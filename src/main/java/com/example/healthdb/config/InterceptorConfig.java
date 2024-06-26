package com.example.healthdb.config;

import com.example.healthdb.handler.JWTInterceptoers;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptoers())
                .addPathPatterns("/user/*")
                .addPathPatterns("/patient/*")
                .addPathPatterns("/hospital/*")
                .addPathPatterns("/orders/*")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register");
    }
}

