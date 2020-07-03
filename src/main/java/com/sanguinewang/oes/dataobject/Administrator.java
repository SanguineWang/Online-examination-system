package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Description: oes
 * Created by Rice on 2020/7/3 10:43
 */
@Data
@Entity
public class Administrator {
    /**
     * 管理员数据库内部id  关联来自user表的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;

    /**
     * 级联User表
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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

    /**
     * 自动修改时间
     */
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
}
