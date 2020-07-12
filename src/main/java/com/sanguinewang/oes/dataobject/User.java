package com.sanguinewang.oes.dataobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanguinewang.oes.enums.RoleEnums;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Description: oes
 * Created by Rice on 2020/7/3 10:35
 */
@Data
@Entity
@NoArgsConstructor
public class User {

    /**
     * User 数据库内部数据表Id
     */
    @Id
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户对外显示账号
     */
    @Column(unique = true)
    private Integer number;

    /**
     * 用户密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//只能进行反序列化 json->java object
    private String password;

    /**
     * 用户角色 （teacher student admin）
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private RoleEnums role;

    /**
     * 自动插入时间
     */
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
}
