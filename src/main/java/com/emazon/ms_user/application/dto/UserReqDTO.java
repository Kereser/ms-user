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
    @Size(min = ConsUtils.LENGHT_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String name;

    @NotBlank
    @Size(min = ConsUtils.LENGHT_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String lastName;

    @NotNull
    @Positive
    @Min(10000)
    private Long idNumber;

    @NotBlank
    @Pattern(regexp = "^(\\+57\\d{10}|\\d{10})$", message = ConsUtils.TELEPHONE_NUMBER_ERROR)
    @Size(min = ConsUtils.LENGHT_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String number;

    @NotNull
    @Pattern(regexp = "^(\\d{4}-\\d{2}-\\d{2})$", message = ConsUtils.DATE_ERROR)
    private String birthDate;

    @NotBlank
    @Email(message = ConsUtils.EMAIL_ERROR)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{7,}$", message = ConsUtils.PASSWORD_ERROR)
    private String password;
}
