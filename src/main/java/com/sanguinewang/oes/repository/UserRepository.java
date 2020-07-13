package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.User;
import com.sanguinewang.oes.enums.RoleEnums;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseReporsitory<User, Integer> {

    @Query("from User u where u.name = :name")
    Optional<User> findByName(String name);

    @Query("from User u where u.number=:number")
    Optional<User> findByNumber(int number);

    @Query("from User u where u.id=:id")
    Optional<User> findById(int id);

    @Modifying
    @Transactional
    @Query("delete from User u  where u.number=:num")
    void deleteByNumber( Integer num);

    @Query("from User u where u.role=:role")
    List<User> findByRole(RoleEnums role);

    @Query("from User u")
    List<User> list();

}
