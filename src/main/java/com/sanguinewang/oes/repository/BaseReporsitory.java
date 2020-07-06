package com.sanguinewang.oes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseReporsitory<T, ID> extends JpaRepository<T, ID> {
    T refresh(T t);

}
