package com.emazon.ms_user.domain.api;

import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.domain.model.User;

public interface IUserServicePort {
    void createUser(User user, RoleEnum type);
    void createAuxDepotUser(User user);
    void createClientUser(User user);
}
