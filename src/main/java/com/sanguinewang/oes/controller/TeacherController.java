package com.sanguinewang.oes.controller;

import com.sanguinewang.oes.dataobject.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 9:17
 */
@RestController
@RequestMapping("api/teacher/")
public class TeacherController {

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("测试redis")
    @GetMapping("redis")
    public Map redis() {
        User user = new User();
        user.setName("wang");
        user.setNumber(1234);
        redisTemplate.opsForValue().set("1", user);

        return Map.of("user", redisTemplate.opsForValue().get("1"));

    }
}
