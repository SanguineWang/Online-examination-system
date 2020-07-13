package com.sanguinewang.oes.interceptor;

import com.sanguinewang.oes.component.RequestComponent;
import com.sanguinewang.oes.enums.RoleEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Description: oes
 * Created by Rice on 2020/7/6 18:14
 */
@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    RequestComponent requestComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("{}", requestComponent.getRole());
        if (requestComponent.getRole() != RoleEnums.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "无权限");
        }
        log.debug("有admin权限");
        return true;
    }
}
