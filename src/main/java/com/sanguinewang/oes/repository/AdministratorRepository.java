package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Administrator;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends BaseReporsitory<Administrator,Integer> {
    @Modifying
    @Query("delete from Administrator a where a.user.number=:number")
    void deleteByNumber(Integer number);

    @Query("from Administrator a where a.user.number=:number")
    <Optional>Administrator findByNumber (Integer number);

    @Modifying
    @Query("delete from Administrator a where a.id=:id")
    void deleteById (Integer id);
}
