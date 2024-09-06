package com.emazon.ms_user.domain.usecases;

import com.emazon.ms_user.DateUtils;
import com.emazon.ms_user.domain.api.IUserServicePort;
import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.domain.model.User;
import com.emazon.ms_user.domain.spi.IUserPersistencePort;
import com.emazon.ms_user.infra.exception.EmailAlreadyExists;
import com.emazon.ms_user.infra.exception.UnderAgeException;

import java.util.Optional;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void createUser(User user, RoleEnum role) {
        Optional<User> optUser = userPersistencePort.findByEmail(user.getEmail());

        if (optUser.isPresent()) throw new EmailAlreadyExists();
        if (Boolean.FALSE.equals(DateUtils.isOlderEighteen(user.getBirthDate()))) {
            throw new UnderAgeException();
        }

        userPersistencePort.createUser(user, role);
    }
}
