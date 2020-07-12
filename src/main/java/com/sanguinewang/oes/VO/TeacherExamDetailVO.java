package com.sanguinewang.oes.VO;

import com.sanguinewang.oes.dataobject.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 教师考试列表模板
 * @Author SanguineWang
 * @Date 2020-07-12 15:49
 */
@Data
public class TeacherExamDetailVO {
    private Integer id;
    //考试名
    private String name;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;

    //学生列表
    private List<Student_Exam> studentExams;

    //选择题列表 一个考试对应多个选择题
    private List<Choice> choiceList;
    //判断题列表 一个考试对应多个判断题
    private List<Judgment> judgmentList;
    //主观题列表 一个考试对应多个主观题
    private List<Subjective> subjectiveList;
}
