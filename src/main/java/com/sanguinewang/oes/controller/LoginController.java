package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.dataobject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 9:17
 */
@RestController
@RequestMapping("api/")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("login")
    public Map login(@RequestBody User login, HttpServletResponse response) {
        return Map.of();
    }
}
