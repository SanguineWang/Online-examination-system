package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Student_Subjective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Student_SubjectiveRepository extends BaseReporsitory<Student_Subjective, Integer> {

    @Query("from Student_Subjective ss where ss.student.id=:sid and ss.subjective.exam.id=:eid")
    List<Student_Subjective> findByExamIdAndStudentId(@Param("sid") Integer sid, @Param("eid") Integer eid);

}
