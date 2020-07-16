package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author SanguineWang
 * @Date 2020-07-03 14:17
 */
@Data
@Entity
@NoArgsConstructor
public class Subjective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //题目
    private String title;
    //答案
//    教师端需要查看
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String answer;
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
    //答题记录
    @OneToMany(mappedBy = "subjective", cascade = CascadeType.REMOVE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Student_Subjective> student_subjectives;
}
