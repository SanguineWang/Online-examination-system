package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends BaseReporsitory<User, Integer> {

    @Query("from User u where u.name = :name")
    Optional<User> findByName(String name);

}
