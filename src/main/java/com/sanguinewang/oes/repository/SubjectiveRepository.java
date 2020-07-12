package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Subjective;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubjectiveRepository extends  BaseReporsitory<Subjective,Integer> {
    @Modifying
    @Transactional
    @Query("delete from Subjective s where s.exam.id=:eid")
    void deleteByExamId(@Param("eid") Integer eid);
}
