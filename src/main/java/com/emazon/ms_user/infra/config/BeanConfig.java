package com.emazon.ms_user.infra.config;

import com.emazon.ms_user.domain.api.IUserServicePort;
import com.emazon.ms_user.domain.model.IPasswordEncoder;
import com.emazon.ms_user.domain.spi.IUserPersistencePort;
import com.emazon.ms_user.domain.usecases.UserUseCase;
import com.emazon.ms_user.infra.out.jpa.adapter.UserJpaAdapter;
import com.emazon.ms_user.infra.out.jpa.mapper.UserEntityMapper;
import com.emazon.ms_user.infra.out.jpa.repository.RoleJpaRepository;
import com.emazon.ms_user.infra.out.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final UserEntityMapper userEntityMapper;
    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final IPasswordEncoder passwordEncoder;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userEntityMapper, userJpaRepository, roleJpaRepository);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), passwordEncoder);
    }
}
