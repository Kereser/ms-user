package com.emazon.ms_user.application.dto;


import com.emazon.ms_user.ConsUtils;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserReqDTO {

    @NotBlank
    @Size(min = ConsUtils.MIN_DEFAULT_FIELD_SIZE, max = ConsUtils.FIELD_SIZE_OF_TWENTY)
    private String name;

    @NotBlank
    @Size(min = ConsUtils.MIN_DEFAULT_FIELD_SIZE, max = ConsUtils.FIELD_SIZE_OF_TWENTY)
    private String lastName;

    @NotNull
    @Positive
    @Min(10000)
    private Long idNumber;

    @NotBlank
    @Pattern(regexp = "")
    private String number;

    @NotNull
    private LocalDate birthDate;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{7,20}$")
    private String password;
}
