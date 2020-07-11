package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 选择题
 * @Author SanguineWang
 * @Date 2020-07-03 14:16
 */
@Data
@Entity
@NoArgsConstructor
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //题目
    private String title;

    //选项
    private String option_A;
    private String option_B;
    private String option_C;
    private String option_D;

    /**
     * 答案
     * A:1
     * B:2
     * C:4
     * D:8
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer answer;
    //分数，老师新建exam的时候设置题目的分数。
    private Float score;

    /**
     * 自动插入时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    /**
     * 题目与考试多对一关系
     */
    @ManyToOne
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Exam exam;
}
