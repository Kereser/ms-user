package com.emazon.ms_user.infra.out.jpa.entity;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.domain.model.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = ConsUtils.FALSE, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(nullable = ConsUtils.FALSE)
    private String description;
}
