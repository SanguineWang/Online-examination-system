package com.sanguinewang.oes.component;

import com.sanguinewang.oes.enums.RoleEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Slf4j
public class RequestComponent {
    public int getUid() {//获取线程级的attribute
        return (int) RequestContextHolder.currentRequestAttributes()
                .getAttribute(MyToken.UID, RequestAttributes.SCOPE_REQUEST);
    }

    public RoleEnums getRole() {
        return (RoleEnums) RequestContextHolder
                .currentRequestAttributes()
                .getAttribute(MyToken.ROLE, RequestAttributes.SCOPE_REQUEST);
    }


}
