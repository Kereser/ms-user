package com.emazon.ms_user.application.handler;

import com.emazon.ms_user.application.dto.AuthResDTO;
import com.emazon.ms_user.application.dto.UserReqDTO;

public interface IUserHandler {
    void createClientUser(UserReqDTO dto);
    void createAuxDepotUser(UserReqDTO dto);
    AuthResDTO login();
}
