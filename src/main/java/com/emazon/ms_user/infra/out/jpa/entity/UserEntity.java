package com.emazon.ms_user.infra.out.jpa.entity;

import com.emazon.ms_user.ConsUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = ConsUtils.FALSE, unique = true)
    private Long id;

    @Column(nullable = ConsUtils.FALSE)
    @Size(min = ConsUtils.LENGTH_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String name;

    @Column(nullable = ConsUtils.FALSE)
    @Size(min = ConsUtils.LENGTH_OF_3, max = ConsUtils.LENGTH_OF_20)
    private String lastName;

    @Column(nullable = ConsUtils.FALSE)
    private Long idNumber;

    @Column(nullable = ConsUtils.FALSE)
    @Size(min = ConsUtils.LENGTH_OF_10, max = ConsUtils.LENGTH_OF_13)
    private String number;

    @Column(nullable = ConsUtils.FALSE)
    private LocalDate birthDate;

    @Column(nullable = ConsUtils.FALSE, unique = true)
    private String email;

    @Column(nullable = ConsUtils.FALSE)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = ConsUtils.FALSE)
    private RoleEnum role;
}
