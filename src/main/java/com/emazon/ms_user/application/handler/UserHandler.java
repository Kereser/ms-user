package com.emazon.ms_user.application.handler;

import com.emazon.ms_user.application.dto.AuthResDTO;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.application.mapper.UserDTOMapper;
import com.emazon.ms_user.domain.api.IUserServicePort;
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
    public void createClientUser(UserReqDTO dto) {
        userServicePort.createClientUser(userDTOMapper.toUser(dto));
    }

    @Override
    public void createAuxDepotUser(UserReqDTO dto) {
        userServicePort.createAuxDepotUser(userDTOMapper.toUser(dto));
    }

    @Override
    public AuthResDTO login() {
        return AuthResDTO.builder()
                .token(JwtUtils.createToken(SecurityContextHolder.getContext().getAuthentication()))
                .build();
    }
}
