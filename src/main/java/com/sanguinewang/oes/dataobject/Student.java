package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: oes
 * Created by Rice on 2020/7/3 10:42
 */
@Data
@Entity
public class Student {
    /**
     * 学生数据库内部id  关联来自user表的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    private User user;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
}
