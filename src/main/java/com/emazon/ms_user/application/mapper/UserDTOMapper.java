package com.emazon.ms_user.application.mapper;

import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapper {

    @Mapping(source = "email", target = "username")
    User toUser(UserReqDTO dto);
}
