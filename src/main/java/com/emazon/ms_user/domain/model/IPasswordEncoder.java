package com.emazon.ms_user.domain.model;

public interface IPasswordEncoder {
    String encode(CharSequence rawPassword);
}
