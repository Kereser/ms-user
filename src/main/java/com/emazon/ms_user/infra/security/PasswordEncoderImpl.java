package com.emazon.ms_user.infra.security;

import com.emazon.ms_user.domain.model.IPasswordEncoder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PasswordEncoderImpl implements IPasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }
}
