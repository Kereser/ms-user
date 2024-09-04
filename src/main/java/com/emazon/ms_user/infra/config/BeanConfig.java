package com.emazon.ms_user.infra.config;

import com.emazon.ms_user.domain.api.IUserServicePort;
import com.emazon.ms_user.domain.spi.IUserPersistencePort;
import com.emazon.ms_user.domain.use_cases.UserUseCase;
import com.emazon.ms_user.infra.out.jpa.adapter.UserJpaAdapter;
import com.emazon.ms_user.infra.out.jpa.mapper.UserEntityMapper;
import com.emazon.ms_user.infra.out.jpa.repository.UserJpaRepository;
import com.emazon.ms_user.infra.security.IBCryptEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final UserEntityMapper userEntityMapper;
    private final UserJpaRepository userJpaRepository;
    private final IBCryptEncoder passwordEncoder;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userEntityMapper, userJpaRepository, passwordEncoder);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }
}
