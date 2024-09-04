package com.emazon.ms_user.domain.use_cases;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.application.dto.RolesReq;
import com.emazon.ms_user.domain.model.User;
import com.emazon.ms_user.domain.spi.IUserPersistencePort;
import com.emazon.ms_user.infra.exception.EmailAlreadyExists;
import com.emazon.ms_user.infra.exception.UnderAgeException;
import com.emazon.ms_user.infra.out.jpa.entity.RoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validUser = new User(
            ConsUtils.LONG_ONE,
            ConsUtils.NAME,
            ConsUtils.LAST_NAME,
            ConsUtils.ID_NUMBER,
            ConsUtils.NUMBER,
            LocalDate.now().minusYears(20),
            ConsUtils.EMAIL,
            ConsUtils.PASSWORD,
            RoleEnum.USER);

    @Test
    void Should_ThrowsException_When_UserAlreadyExists() {
        Mockito.doReturn(Optional.of(validUser)).when(userPersistencePort).findByEmail(Mockito.anyString());

        assertThrows(EmailAlreadyExists.class, () -> userUseCase.createUser(validUser, RolesReq.AUX_DEPOT));
    }

    @Test
    void Should_ThrowsException_When_UnderAge() {
        Mockito.doReturn(Optional.empty()).when(userPersistencePort).findByEmail(Mockito.anyString());
        validUser.setBirthDate(LocalDate.now());
        assertThrows(UnderAgeException.class, () -> userUseCase.createUser(validUser, RolesReq.AUX_DEPOT));
    }

    @Test
    void Should_SaveUser_When_ValidPayload() {
        Mockito.doReturn(Optional.empty()).when(userPersistencePort).findByEmail(Mockito.anyString());

        userUseCase.createUser(validUser, RolesReq.AUX_DEPOT);

        Mockito.verify(userPersistencePort, Mockito.times(1)).createUser(validUser);
        Mockito.verify(userPersistencePort, Mockito.times(1)).findByEmail(Mockito.anyString());
    }
}