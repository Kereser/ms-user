package com.emazon.ms_user.application.handler;

import com.emazon.ms_user.application.dto.RolesReq;
import com.emazon.ms_user.application.dto.UserReqDTO;

public interface IUserHandler {
    void createUser(UserReqDTO dto, RolesReq type);
}
