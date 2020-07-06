package com.sanguinewang.oes.component;

import com.sanguinewang.oes.enums.RoleEnums;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 10:43
 */
@Data
@AllArgsConstructor
public class MyToken {
    public static final String AUTHORIZATION = "Authorization";
    public static final String UID = "uid";
    public static final String ROLE = "role";
    private Integer uid;
    private RoleEnums role;
}
