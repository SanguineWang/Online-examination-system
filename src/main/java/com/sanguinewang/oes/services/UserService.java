package com.sanguinewang.oes.services;

import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 10:13
 */
@Service("UserService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(User user) {
        return userRepository.findByName(user.getName()).orElse(null);
//        return userRepository.findByUsername(user.getUsername());
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
//        return userMapper.findUserById(userId);
    }
}
