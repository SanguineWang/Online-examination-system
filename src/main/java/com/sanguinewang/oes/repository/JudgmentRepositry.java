package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Judgment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 21:15
 */
@Repository
public interface JudgmentRepositry  extends BaseReporsitory<Judgment,Integer>{
    @Modifying
    @Transactional
    @Query("delete from Judgment j where j.exam.id=:eid")
    void deleteByExamId(@Param("eid") Integer eid);
}
