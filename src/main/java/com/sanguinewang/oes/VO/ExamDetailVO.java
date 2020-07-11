package com.sanguinewang.oes.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanguinewang.oes.dataobject.*;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: oes
 * Created by Rice on 2020/7/11 0:50
 */
@Data
public class ExamDetailVO {

    //考试名
    private String name;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;
    //选择题列表 一个考试对应多个选择题
    private List<Choice> choiceList;
    //判断题列表 一个考试对应多个判断题
    private List<Judgment> judgmentList;
    //主观题列表 一个考试对应多个主观题
    private List<Subjective> subjectiveList;
}
