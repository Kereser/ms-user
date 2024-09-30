package com.emazon.ms_user.infra.exception;

import com.emazon.ms_user.infra.out.jpa.entity.UserEntity;

public class EmailAlreadyExists extends BaseEntityException {
    private static final String EMAIL = "email";

    public EmailAlreadyExists() {
        super(UserEntity.class.getSimpleName(), EMAIL);
    }
}
