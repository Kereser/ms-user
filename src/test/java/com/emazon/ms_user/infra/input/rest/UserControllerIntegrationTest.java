package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.MvcUtils;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

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

    private String validReqUserJSON;

    private static final String USER = "testuser";
    private static final String PASSWORD = "password";
    private static final Long USER_ID = 1l;

    private static final String AUTHORIZATION = "Authorization";
    private static final String LOGIN_ENDPOINT = "/users/login";
    private static final String BEARER = "Bearer ";
    private static final String BASIC_DEFAULT_AUTH = "Basic " +
            Base64.getEncoder().encodeToString((USER + ":" + PASSWORD).getBytes());

    private static final UserEntity USER_ENTITY = UserEntity.builder().username(USER)
            .password("{noop}" + PASSWORD)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .enabled(true)
            .credentialsNonExpired(true)
            .roles(Set.of(RoleEntity.builder().name(RoleEnum.ADMIN).build()))
            .build();

    @Test
    void Should_SaveAuxDepot_When_ValidPayloadAndValidation() throws Exception {
        validReqUserJSON = mapper.writeValueAsString(USER_REQ_DTO);
        postWithAdminToken(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void Should_GetValidErrorStructure_When_EmailExistsOrUnderAge() throws Exception {
        USER_REQ_DTO.setBirthDate(LocalDate.now().toString());
        validReqUserJSON = mapper.writeValueAsString(USER_REQ_DTO);
        ResultActions res = sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM));

        // Birthdate error
        res.andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.USER_MUST_BE_OLDER))
                .andExpect(jsonPath(ConsUtils.FIELD_BIRTH_DATE_PATH).value(ExceptionResponse.BIRTH_DAY_CONSTRAINS));

        // Email already exists.
        USER_REQ_DTO.setBirthDate(ConsUtils.BIRTH_DATE_STRING);
        validReqUserJSON = mapper.writeValueAsString(USER_REQ_DTO);
        sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM)).andExpect(status().isCreated());

        sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.EMAIL_CONSTRAINS))
                .andExpect(jsonPath(ConsUtils.FIELD_EMAIL_PATH).value(ExceptionResponse.EMAIL_MUST_BE_UNIQUE));
    }

    /*** Security ***/

    @Test
    void Should_ThrowsException_When_NonAuthenticatedUser() throws Exception {
        validReqUserJSON = mapper.writeValueAsString(USER_REQ_DTO);
        sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void Should_ThrowsException_When_NotValidRoles() throws Exception {
        validReqUserJSON = mapper.writeValueAsString(USER_REQ_DTO);
        sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM))
                .andExpect(status().isForbidden());
    }

    @Test
    void Should_GetValidToken_When_ValidCredentials() throws Exception {
        Mockito.doReturn(Optional.of(USER_ENTITY))
                .when(userJpaRepository).findByUsername(Mockito.anyString());

        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get(LOGIN_ENDPOINT)
                            .header(AUTHORIZATION, BASIC_DEFAULT_AUTH))
                            .andExpect(status().isOk());

        res.andExpect(jsonPath(ConsUtils.FIELD_TOKEN).isNotEmpty());
    }

    @Test
    void Should_ThrowsException_When_InvalidUserInfo() throws Exception {
        Mockito.doReturn(Optional.empty())
                .when(userJpaRepository).findByUsername(Mockito.anyString());

        mockMvc.perform(MockMvcRequestBuilders.get(LOGIN_ENDPOINT)
            .header(AUTHORIZATION, BASIC_DEFAULT_AUTH))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void Should_ThrowsException_When_InvalidToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOGIN_ENDPOINT)
                        .header(AUTHORIZATION, BEARER + " "))
                .andExpect(status().isUnauthorized());
    }

    private ResultActions sentPostToCreateEntity(String dto, String url, MultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .params(params)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions postWithAdminToken(String dto, String url, MultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header(AUTHORIZATION, BEARER + getAdminToken())
                .params(params)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private String getAdminToken() {
        CustomUserDetails userDetail = new CustomUserDetails(USER, PASSWORD, Set.of(new SimpleGrantedAuthority("ROLE_".concat("ADMIN"))), USER_ID);
        return JwtUtils.createToken(new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()));
    }
}
