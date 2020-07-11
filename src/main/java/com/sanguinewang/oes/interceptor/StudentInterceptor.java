package com.sanguinewang.oes.interceptor;

import com.sanguinewang.oes.component.RequestComponent;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 18:10
 * 学生权限拦截器
 */
@Component
public class StudentInterceptor implements HandlerInterceptor {

    @Autowired
    RequestComponent requestComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (requestComponent.getRole() != RoleEnums.STUDENT) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "权限不足");
        }
        return true;
    }
}
