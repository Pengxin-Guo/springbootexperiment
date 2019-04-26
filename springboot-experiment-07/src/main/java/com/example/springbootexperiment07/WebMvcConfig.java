package com.example.springbootexperiment07;

import com.example.springbootexperiment07.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration   // 声明为配置类
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)       // 注入拦截器
                .addPathPatterns("/api/**")             // 拦截路径规则
                .excludePathPatterns("/api/login")      // 拦截排除规则
                .excludePathPatterns("/api/register");  // 拦截排除规则
    }
}
