package com.emazon.ms_user.application.mapper;

import com.emazon.ms_user.application.dto.AuthReqDTO;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.domain.model.AuthSession;
import com.emazon.ms_user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SessionDTOMapper {
    AuthSession toSession(AuthReqDTO dto);
    User toUser(UserReqDTO dto);
}