package com.emazon.ms_user.infra.out.jpa.mapper;

import com.emazon.ms_user.domain.model.User;
import com.emazon.ms_user.infra.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    User toUser(UserEntity entity);
    default Optional<User> toOptUser(Optional<UserEntity> optEntity) {
        return optEntity.map(this::toUser);
    }
    UserEntity toUserEntity(User user);
}
