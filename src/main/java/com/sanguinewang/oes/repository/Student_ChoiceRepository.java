package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Student_Choice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-06 9:10
 */
@Repository
public interface Student_ChoiceRepository extends BaseReporsitory<Student_Choice, Integer> {

    @Query("select  sc.score from Student_Choice  sc where  sc.student.id = :uid and sc.choice.exam.id=:eid")
    List<Float> listScoreByStudentIdAndExamId(Integer uid, Integer eid);
}
