package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Exam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends BaseReporsitory<Exam, Integer> {
    @Query("from Exam ex where ex.id = :eid")
    Optional<Exam> findExamByExamId(Integer eid);

    @Query("from Exam e where e.startTime=:startTime")
    List<Exam> findByStartTime(LocalDateTime startTime);

    @Query("from Exam e")
    List<Exam> list();

}
