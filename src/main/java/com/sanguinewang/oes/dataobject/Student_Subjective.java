package com.sanguinewang.oes.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Description 学生主观题答案
 * @Author SanguineWang
 * @Date 2020-07-03 15:58
 */
@Data
@Entity
@NoArgsConstructor
public class Student_Subjective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //  学生
    @ManyToOne
    private Student student;
    // 主观题
    @ManyToOne
    private Subjective subjective;
    //学生的答案
    private String answer;
    //老师批改的，学生的分数
    private Float score;
}
