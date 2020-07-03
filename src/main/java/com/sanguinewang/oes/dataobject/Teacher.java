package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: oes
 * Created by Rice on 2020/7/3 10:38
 */
@Data
@Entity
public class Teacher {
    /**
     * 教师数据库内部id  关联来自user表的id
     */
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 相关user的级联
     */
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @MapsId
    private User user;

    /**
     * 自动插入时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

}
