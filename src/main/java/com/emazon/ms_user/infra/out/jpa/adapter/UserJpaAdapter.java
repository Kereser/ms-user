package com.emazon.ms_user.infra.out.jpa.adapter;

import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.domain.model.User;
import com.emazon.ms_user.domain.spi.IUserPersistencePort;
import com.emazon.ms_user.infra.out.jpa.entity.RoleEntity;
import com.emazon.ms_user.infra.out.jpa.entity.UserEntity;
import com.emazon.ms_user.infra.out.jpa.mapper.UserEntityMapper;
import com.emazon.ms_user.infra.out.jpa.repository.RoleJpaRepository;
import com.emazon.ms_user.infra.out.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final UserEntityMapper userEntityMapper;
    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public void createUser(User user, RoleEnum role) {
        RoleEntity roleEntity = roleJpaRepository.findByName(role);
        UserEntity entity = userEntityMapper.toUserEntity(user);

        entity.addRoles(Set.of(roleEntity));

        userJpaRepository.save(entity);
    }

    public Optional<User> findByUsername(String username) {
        return userEntityMapper.toOptUser(userJpaRepository.findByUsername(username));
    }
}
