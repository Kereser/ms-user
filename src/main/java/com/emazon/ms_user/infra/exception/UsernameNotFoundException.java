package com.emazon.ms_user.infra.exception;

import com.emazon.ms_user.infra.exceptionhandler.ExceptionResponse;
import org.springframework.security.core.AuthenticationException;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException() {
        super(ExceptionResponse.EMAIL_NOT_FOUND);
    }
}
