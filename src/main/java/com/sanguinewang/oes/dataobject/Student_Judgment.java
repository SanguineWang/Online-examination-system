package com.sanguinewang.oes.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Description 学生判断题答案
 * @Author SanguineWang
 * @Date 2020-07-03 16:27
 */
@Data
@Entity
@NoArgsConstructor
public class Student_Judgment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //学生信息
    @ManyToOne
    private Student student;

    //题目
    @ManyToOne
    private Choice choice;


    //学生的答案
    private Integer answer;
    //系统判断的，学生的分数
    private Float score;
}
