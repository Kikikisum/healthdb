package com.example.healthdb.config;

import com.example.healthdb.model.handler.JWTInterceptoers;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptoers())
                .addPathPatterns("/user/*")  // 其他接口token验证
                .addPathPatterns("/user/update/*")
                .addPathPatterns("/patient/*")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register");  // 所有用户都放行
    }
}

