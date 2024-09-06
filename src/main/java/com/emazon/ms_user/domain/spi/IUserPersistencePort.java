package com.emazon.ms_user.domain.spi;

import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {
    Optional<User> findByEmail(String name);
    void createUser(User user, RoleEnum role);
}
