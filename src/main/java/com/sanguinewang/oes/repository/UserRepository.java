package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends BaseReporsitory<User, Integer> {

    @Query("from User u where u.name = :name")
    Optional<User> findByName(String name);

}
