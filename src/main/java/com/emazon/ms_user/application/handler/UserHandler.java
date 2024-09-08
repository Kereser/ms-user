package com.emazon.ms_user.application.handler;

import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.application.mapper.UserDTOMapper;
import com.emazon.ms_user.domain.api.IUserServicePort;
import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.infra.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final UserDTOMapper userDTOMapper;

    @Override
    public void createUser(UserReqDTO dto, RoleEnum role) {
        userServicePort.createUser(userDTOMapper.toUser(dto), role);
    }

    @Override
    public String login() {
        return JwtUtils.createToken(SecurityContextHolder.getContext().getAuthentication());
    }

}
