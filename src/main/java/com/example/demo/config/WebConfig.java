package com.example.demo.config;

import com.example.demo.config.interceptor.JwtAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //     제외대상 회원가입, 동네 조회, 일반 로그인
    private String[] INTERCEPTOR_WHITE_LIST = {
            "/app/users/sign-up", "/app/users", "/app/users/sign-in", "/app/events/**", "/app/categories/**"
    };

    @Autowired
    JwtAuthInterceptor jai;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jai).addPathPatterns("/**").excludePathPatterns(INTERCEPTOR_WHITE_LIST);
    }
}
