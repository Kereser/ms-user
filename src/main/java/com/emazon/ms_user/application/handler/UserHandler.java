package com.emazon.ms_user.application.handler;

import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.application.mapper.SessionDTOMapper;
import com.emazon.ms_user.domain.api.IUserServicePort;
import com.emazon.ms_user.domain.model.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort sessionServicePort;
    private final SessionDTOMapper sessionDTOMapper;

    @Override
    public void createUser(UserReqDTO dto, RoleEnum role) {
        sessionServicePort.createUser(sessionDTOMapper.toUser(dto), role);
    }
}
