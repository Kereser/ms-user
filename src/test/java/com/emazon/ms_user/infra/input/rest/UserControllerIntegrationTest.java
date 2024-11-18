package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.domain.model.RoleEnum;
import com.emazon.ms_user.infra.exceptionhandler.ExceptionResponse;
import com.emazon.ms_user.infra.out.jpa.entity.RoleEntity;
import com.emazon.ms_user.infra.out.jpa.entity.UserEntity;
import com.emazon.ms_user.infra.out.jpa.repository.UserJpaRepository;
import com.emazon.ms_user.infra.security.service.model.CustomUserDetails;
import com.emazon.ms_user.infra.security.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @SpyBean
    private UserJpaRepository userJpaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static final UserReqDTO USER_REQ_DTO = new UserReqDTO(
            ConsUtils.NAME,
            ConsUtils.LAST_NAME,
            ConsUtils.ID_NUMBER,
            ConsUtils.NUMBER,
            ConsUtils.BIRTH_DATE_STRING,
            ConsUtils.EMAIL,
            ConsUtils.PASSWORD);

    private static final String PASSWORD = "password";
    private static final String BASIC_DEFAULT_AUTH = "Basic " +
            Base64.getEncoder().encodeToString((ConsUtils.USER + ConsUtils.COLON_DELIMITER + PASSWORD).getBytes());


    private static final UserEntity USER_ENTITY = UserEntity.builder().username(ConsUtils.USER)
            .password(ConsUtils.NOOP_PASSWORD + PASSWORD)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .enabled(true)
            .credentialsNonExpired(true)
            .roles(Set.of(RoleEntity.builder().name(RoleEnum.ADMIN).build()))
            .build();

    @Test
    void Should_SaveAuxDepot_When_Valid() throws Exception {
        postWithAdminToken(mapper.writeValueAsString(USER_REQ_DTO), ConsUtils.builderPath().withAuxDepot().build())
                .andExpect(status().isCreated());
    }

    @Test
    void Should_SaveClient_When_Valid() throws Exception {
        postWithAdminToken(mapper.writeValueAsString(USER_REQ_DTO), ConsUtils.builderPath().withClient().build())
                .andExpect(status().isCreated());
    }

    @Test
    void Should_ThrowsException_When_NotBodyPresent() throws Exception {
        mockMvc.perform(post(ConsUtils.builderPath().withClient().build())).andExpect(status().isBadRequest());
    }

    @Test
    void Should_GetValidErrorStructure_When_EmailExistsOrUnderAge() throws Exception {
        USER_REQ_DTO.setBirthDate(LocalDate.now().toString());

        // Birthdate error
        postWithAdminToken(mapper.writeValueAsString(USER_REQ_DTO), ConsUtils.builderPath().withAuxDepot().build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.USER_MUST_BE_OLDER))
                .andExpect(jsonPath(ConsUtils.FIELD_BIRTH_DATE_PATH).value(ExceptionResponse.BIRTH_DAY_CONSTRAINS));

        // Email already exists.
        USER_REQ_DTO.setBirthDate(ConsUtils.BIRTH_DATE_STRING);
        postWithAdminToken(mapper.writeValueAsString(USER_REQ_DTO), ConsUtils.builderPath().withAuxDepot().build())
                .andExpect(status().isCreated());

        postWithAdminToken(mapper.writeValueAsString(USER_REQ_DTO), ConsUtils.builderPath().withAuxDepot().build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.EMAIL_CONSTRAINS))
                .andExpect(jsonPath(ConsUtils.FIELD_EMAIL_PATH).value(ExceptionResponse.EMAIL_MUST_BE_UNIQUE));
    }


    /*** Security ***/
    @Test
    void Should_ThrowsException_When_NotAuthenticatedUser() throws Exception {
        mockMvc.perform(post(ConsUtils.builderPath().withAuxDepot().build())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(mapper.writeValueAsString(USER_REQ_DTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void Should_ThrowsException_When_NotValidRoles() throws Exception {
        mockMvc.perform(post(ConsUtils.builderPath().withAuxDepot().build()).content(mapper.writeValueAsString(USER_REQ_DTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void Should_GetValidToken_When_ValidCredentials() throws Exception {
        Mockito.doReturn(Optional.of(USER_ENTITY))
                .when(userJpaRepository).findByUsername(Mockito.anyString());

        mockMvc.perform(get(ConsUtils.LOGIN_ENDPOINT)
                    .header(ConsUtils.AUTHORIZATION, BASIC_DEFAULT_AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(ConsUtils.FIELD_TOKEN).isNotEmpty());

        Mockito.reset(userJpaRepository);
    }

    @Test
    void Should_ThrowsException_When_InvalidUserInfo() throws Exception {
        Mockito.doReturn(Optional.empty())
                .when(userJpaRepository).findByUsername(Mockito.anyString());

        mockMvc.perform(get(ConsUtils.LOGIN_ENDPOINT)
            .header(ConsUtils.AUTHORIZATION, BASIC_DEFAULT_AUTH))
            .andExpect(status().isUnauthorized());

        Mockito.reset(userJpaRepository);
    }

    @Test
    void Should_ThrowsException_When_InvalidToken() throws Exception {
        mockMvc.perform(get(ConsUtils.LOGIN_ENDPOINT)
                        .header(ConsUtils.AUTHORIZATION, ConsUtils.BEARER + ConsUtils.SPACE))
                .andExpect(status().isUnauthorized());
    }

    private ResultActions postWithAdminToken(String dto, String url) throws Exception {
        return mockMvc.perform(post(url)
                .header(ConsUtils.AUTHORIZATION, ConsUtils.BEARER + getAdminToken())
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private String getAdminToken() {
        CustomUserDetails userDetail = new CustomUserDetails(ConsUtils.USER, PASSWORD, Set.of(new SimpleGrantedAuthority(ConsUtils.ROLE.concat(ConsUtils.ADMIN))), ConsUtils.USER_ID_1);
        return JwtUtils.createToken(new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()));
    }
}
