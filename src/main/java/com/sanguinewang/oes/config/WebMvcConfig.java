package com.sanguinewang.oes.config;

import com.sanguinewang.oes.interceptor.AuthenticationInterceptor;
import com.sanguinewang.oes.interceptor.StudentInterceptor;
import com.sanguinewang.oes.interceptor.TeacherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 10:23
 * 配置拦截器
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    private TeacherInterceptor teacherInterceptor;
    @Autowired
    private StudentInterceptor studentInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(teacherInterceptor)
                .addPathPatterns("/api/teachers/**");
        registry.addInterceptor(studentInterceptor)
                .addPathPatterns("/api/students/**");
    }



//    @Bean
//    public AuthenticationInterceptor authenticationInterceptor() {
//
//        return new AuthenticationInterceptor();
//    }

}