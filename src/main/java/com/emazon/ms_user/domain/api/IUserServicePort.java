package com.emazon.ms_user.domain.api;

import com.emazon.ms_user.application.dto.RolesReq;
import com.emazon.ms_user.domain.model.User;

public interface IUserServicePort {
    void createUser(User user, RolesReq type);
}
