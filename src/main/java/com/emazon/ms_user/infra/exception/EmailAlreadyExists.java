package com.emazon.ms_user.infra.exception;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.infra.out.jpa.entity.UserEntity;

public class EmailAlreadyExists extends BaseEntityException {
    public EmailAlreadyExists() {
        super(UserEntity.class.getSimpleName(), ConsUtils.EMAIL_STR);
    }
}
