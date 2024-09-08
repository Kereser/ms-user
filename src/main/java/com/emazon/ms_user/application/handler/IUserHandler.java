package com.emazon.ms_user.application.handler;

import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.domain.model.RoleEnum;

public interface IUserHandler {
    void createUser(UserReqDTO dto, RoleEnum role);
    String login();
}
