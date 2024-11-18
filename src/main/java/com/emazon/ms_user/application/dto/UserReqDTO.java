package com.emazon.ms_user.application.dto;


import com.emazon.ms_user.ConsUtils;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDTO {

    @NotBlank
    @Size(min = ConsUtils.LENGTH_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String name;

    @NotBlank
    @Size(min = ConsUtils.LENGTH_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String lastName;

    @NotNull
    @Positive
    @Min(10000)
    private Long idNumber;

    @NotBlank
    @Pattern(regexp = ConsUtils.PHONE_NUMBER_REGEX, message = ConsUtils.TELEPHONE_NUMBER_ERROR)
    @Size(min = ConsUtils.LENGTH_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String number;

    @NotNull
    @Pattern(regexp = ConsUtils.DATE_REGEX, message = ConsUtils.DATE_ERROR)
    private String birthDate;

    @NotBlank
    @Email(message = ConsUtils.EMAIL_ERROR)
    private String email;

    @NotBlank
    @Pattern(regexp = ConsUtils.PASSWORD_REGEX, message = ConsUtils.PASSWORD_ERROR)
    private String password;
}
