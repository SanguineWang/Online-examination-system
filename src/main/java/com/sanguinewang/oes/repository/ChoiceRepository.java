package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Administrator;
import com.sanguinewang.oes.dataobject.Choice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChoiceRepository  extends BaseReporsitory<Choice,Integer>{

    @Modifying
    @Transactional
    @Query("delete from Choice c where c.exam.id=:eid")
    void deleteByExamId(@Param("eid") Integer eid);
}
