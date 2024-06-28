package com.example.healthdb.config;

import com.example.healthdb.handler.CorsInterceptor;
import com.example.healthdb.handler.JWTInterceptoers;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
                .addPathPatterns("/escort/*")
                .addPathPatterns("/admin/*")
                .addPathPatterns("/evaluate/*")
                .addPathPatterns("/file/*")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register");
        // 注册跨域拦截器,设置 order 为 -1
        registry.addInterceptor(new CorsInterceptor()).order(-1);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //支持所有接口
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //支持域
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}

