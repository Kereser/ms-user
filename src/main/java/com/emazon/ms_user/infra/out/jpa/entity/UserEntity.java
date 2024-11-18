package com.emazon.ms_user.infra.out.jpa.entity;

import com.emazon.ms_user.ConsUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String username;

    @Column(nullable = ConsUtils.FALSE)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = ConsUtils.FALSE)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @Builder.Default
    @Column(nullable = ConsUtils.FALSE)
    private boolean enabled = Boolean.TRUE;

    @Builder.Default
    @Column(nullable = ConsUtils.FALSE)
    private boolean accountNonExpired = Boolean.TRUE;

    @Builder.Default
    @Column(nullable = ConsUtils.FALSE)
    private boolean accountNonLocked = Boolean.TRUE;

    @Builder.Default
    @Column(nullable = ConsUtils.FALSE)
    private boolean credentialsNonExpired = Boolean.TRUE;

    public void addRoles(Set<RoleEntity> roles) {
        this.roles.addAll(roles);
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(r -> new SimpleGrantedAuthority(ConsUtils.ROLE.concat(r.getName().name())))
                .toList();
    }
}
