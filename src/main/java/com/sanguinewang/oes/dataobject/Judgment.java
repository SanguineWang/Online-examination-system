package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description 判断题
 * @Author SanguineWang
 * @Date 2020-07-03 16:23
 */
@Data
@Entity
@NoArgsConstructor
public class Judgment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//题目
    private String title;

    /**
     * 答案
     * 对：0
     * 错：1
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer answer;
    //分数
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
