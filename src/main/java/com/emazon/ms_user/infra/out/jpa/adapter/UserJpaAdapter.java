package com.emazon.ms_user.infra.out.jpa.adapter;

import com.emazon.ms_user.domain.model.User;
import com.emazon.ms_user.domain.spi.IUserPersistencePort;
import com.emazon.ms_user.infra.out.jpa.entity.UserEntity;
import com.emazon.ms_user.infra.out.jpa.mapper.UserEntityMapper;
import com.emazon.ms_user.infra.out.jpa.repository.UserJpaRepository;
import com.emazon.ms_user.infra.security.IBCryptEncoder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final UserEntityMapper userEntityMapper;
    private final UserJpaRepository userJpaRepository;
    private final IBCryptEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        UserEntity entity = userEntityMapper.toUserEntity(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));

        userJpaRepository.save(entity);
    }

    public Optional<User> findByEmail(String name) {
        return userEntityMapper.toOptUser(userJpaRepository.findByEmail(name));
    }
}
