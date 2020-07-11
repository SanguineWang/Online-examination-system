package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Student_Exam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Student_ExamRepository extends BaseReporsitory<Student_Exam, Integer> {

    @Query("from  Student_Exam  se where se.student.id=:uid and se.exam.id = :examId")
    Optional<Student_Exam> findExamByStudentUidAndExamId(Integer uid, Integer examId);

    @Modifying
    @Query("update Student_Exam  se set se.submit = true " +
            "where  se.submit=false and se.student.id=:uid and se.exam.id = :examId")
    void updateStudent_ExamSubmitStatus(Integer uid, Integer examId);

    @Query("from Student_Exam se where se.student.id =:uid")
    List<Student_Exam> findExamListByStudentUid(Integer uid);

}
