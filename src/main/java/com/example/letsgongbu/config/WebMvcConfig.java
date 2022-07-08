package com.example.letsgongbu.config;

import com.example.letsgongbu.filter.AutoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoLoginInterceptor());
    }

    @Bean
    public AutoLoginInterceptor autoLoginInterceptor() {
        return new AutoLoginInterceptor();
    }


}
