package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Exam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends BaseReporsitory<Exam, Integer> {
    @Query("from Exam ex where ex.id = :eid")
    Optional<Exam> findExamByExamId(Integer eid);
}
