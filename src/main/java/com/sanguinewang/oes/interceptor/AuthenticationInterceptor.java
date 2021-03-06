package com.sanguinewang.oes.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sanguinewang.oes.annotation.PassToken;
import com.sanguinewang.oes.annotation.UserLoginToken;
import com.sanguinewang.oes.component.MyToken;
import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.enums.RoleEnums;
import com.sanguinewang.oes.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 10:12
 * 获取token并验证token
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("AUTHORIZATION");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;

        Method method = handlerMethod.getMethod();
//        method.getDeclaringClass().isAnnotationPresent()
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                String userRole;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                    userRole = JWT.decode(token).getAudience().get(1);
                    log.info("{}", userId);
                    log.info("{}", userRole);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                User user = userService.findUserById(Integer.valueOf(userId));
                if (user == null) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "用户不存在，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"token失效或过期");
                }
                //如果通过token鉴权 在request中加入 uid 以及 role
                httpServletRequest.setAttribute(MyToken.UID, Integer.valueOf(userId));
                httpServletRequest.setAttribute(MyToken.ROLE, RoleEnums.valueOf(userRole));
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}