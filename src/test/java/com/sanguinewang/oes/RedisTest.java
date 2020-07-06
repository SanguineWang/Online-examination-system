package com.sanguinewang.oes;

import com.sanguinewang.oes.dataobject.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.security.PublicKey;
import java.util.Map;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 10:49
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void redisTest(){
        User user = new User();
        user.setName("wang");
        user.setNumber(1234);
        redisTemplate.opsForValue().set("1", user);
        System.out.println(redisTemplate.opsForValue().get("1"));

    }
}
