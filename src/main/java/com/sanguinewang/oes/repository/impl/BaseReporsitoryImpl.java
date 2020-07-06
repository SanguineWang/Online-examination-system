package com.sanguinewang.oes.repository.impl;

import com.sanguinewang.oes.repository.BaseReporsitory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class BaseReporsitoryImpl<T, ID> extends SimpleJpaRepository<T, ID>  implements BaseReporsitory<T, ID> {
    private EntityManager manager;
    public BaseReporsitoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager=entityManager;
    }

    @Override
    public T refresh(T t) {
      manager.refresh(t);
      return t;
    }


}
