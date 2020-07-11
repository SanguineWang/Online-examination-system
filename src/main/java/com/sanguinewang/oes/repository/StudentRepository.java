package com.sanguinewang.oes.repository;

import com.sanguinewang.oes.dataobject.Exam;
import com.sanguinewang.oes.dataobject.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseReporsitory<Student, Integer> {

    /**
     * @param uid
     * @return Optional student容器
     */
    @Query("from Student stu where stu.id = :uid")
    Optional<Student> findById(Integer uid);

    /**
     * 查询考试是否允许参加，时间是否匹配
     * @param examId
     * @param time
     * @return
     */
    @Query("from Exam e where e.id =  :examId and  :time between e.startTime and e.endTime")
    Optional<Exam> findExamByExamIdAndTime(Integer examId, LocalDateTime time);
}
