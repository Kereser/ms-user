package com.emazon.ms_user.infra.out.jpa.repository;

import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.infra.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(RoleEnum name);
}
