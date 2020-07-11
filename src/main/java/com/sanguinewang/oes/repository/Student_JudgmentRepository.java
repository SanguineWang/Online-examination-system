package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Student_Judgment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Student_JudgmentRepository extends BaseReporsitory<Student_Judgment, Integer> {
    @Query("select  sj.score from Student_Judgment  sj where  sj.student.id = :uid and sj.judgment.exam.id=:eid")
    List<Float> listScoreByStudentIdAndExamId(Integer uid, Integer eid);
}
