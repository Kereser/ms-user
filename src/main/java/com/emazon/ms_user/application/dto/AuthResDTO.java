package com.emazon.ms_user.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResDTO {
    private String token;
}
