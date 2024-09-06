package com.emazon.ms_user.infra.exception;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.domain.model.User;

public class UnderAgeException extends BaseEntityException {
    public UnderAgeException() {
        super(User.class.getSimpleName(), ConsUtils.BIRTH_DATE);
    }
}
