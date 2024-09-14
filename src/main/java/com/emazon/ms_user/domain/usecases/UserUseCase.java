package com.emazon.ms_user.domain.usecases;

import com.emazon.ms_user.DateUtils;
import com.emazon.ms_user.domain.api.IUserServicePort;
import com.emazon.ms_user.domain.model.IPasswordEncoder;
import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.domain.model.User;
import com.emazon.ms_user.domain.spi.IUserPersistencePort;
import com.emazon.ms_user.infra.exception.EmailAlreadyExists;
import com.emazon.ms_user.infra.exception.UnderAgeException;

import java.util.Optional;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoder passwordEncoder;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user, RoleEnum role) {
        Optional<User> optUser = userPersistencePort.findByUsername(user.getUsername());

        if (optUser.isPresent()) throw new EmailAlreadyExists();
        if (Boolean.FALSE.equals(DateUtils.isOlderEighteen(user.getBirthDate()))) {
            throw new UnderAgeException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userPersistencePort.createUser(user, role);
    }

    @Override
    public void createAuxDepotUser(User user) {
        createUser(user, RoleEnum.AUX_DEPOT);
    }

    @Override
    public void createClientUser(User user) {
        createUser(user, RoleEnum.CLIENT);
    }
}
