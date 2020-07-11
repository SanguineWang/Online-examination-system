package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @Description 学生考试记录，包括成绩
 * @Author SanguineWang
 * @Date 2020-07-03 15:10
 */
@Entity
@Data
@NoArgsConstructor
public class Student_Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //学生
    @ManyToOne
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Student student;
    //考试
    @ManyToOne
    @ToString.Exclude
    @JsonProperty()
    private Exam exam;

    private boolean submit = false;

    //主观题答案，系统自动加和，选择判断的分数（系统判）
    private Float objectiveGrade;
    //简答题答案，系统自动加和，主观题成绩（老师判）
    private Float subjectiveGrade;

}
