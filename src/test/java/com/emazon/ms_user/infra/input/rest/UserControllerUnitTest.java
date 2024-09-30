package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.application.handler.UserHandler;
import com.emazon.ms_user.infra.exceptionhandler.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerUnitTest {

    @MockBean
    private UserHandler userHandler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void Should_ThrowsException_When_NotAuthenticated() throws Exception{
        mockMvc.perform(post(ConsUtils.builderPath().withAuxDepot().build())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = ConsUtils.CLIENT)
    void Should_ThrowsException_When_NotValidRole() throws Exception{
        mockMvc.perform(post(ConsUtils.builderPath().withAuxDepot().build())).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = ConsUtils.ADMIN)
    void Should_ThrowsException_When_InvalidPayloadForAuxDepot() throws Exception{
        ResultActions res = sentPostToCreateEntity(mapper.writeValueAsString(new UserReqDTO()), ConsUtils.builderPath().withAuxDepot().build());

        assertFieldErrors(res);
        res.andExpect(jsonPath(ConsUtils.FIELD_NAME_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_EMAIL_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_BIRTH_DATE_PATH).value(ExceptionResponse.NOT_NULL))
                .andExpect(jsonPath(ConsUtils.FIELD_ID_NUMBER_PATH).value(ExceptionResponse.NOT_NULL))
                .andExpect(jsonPath(ConsUtils.FIELD_LAST_NAME_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_PASSWORD_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_NUMBER_PATH).value(ExceptionResponse.NOT_BLANK));
    }

    @Test
    @WithMockUser(roles = ConsUtils.ADMIN)
    void Should_ThrowsException_When_InvalidPayloadForClient() throws Exception{
        ResultActions res = sentPostToCreateEntity(mapper.writeValueAsString(new UserReqDTO()), ConsUtils.builderPath().withClient().build());

        assertFieldErrors(res);
        res.andExpect(jsonPath(ConsUtils.FIELD_NAME_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_EMAIL_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_BIRTH_DATE_PATH).value(ExceptionResponse.NOT_NULL))
                .andExpect(jsonPath(ConsUtils.FIELD_ID_NUMBER_PATH).value(ExceptionResponse.NOT_NULL))
                .andExpect(jsonPath(ConsUtils.FIELD_LAST_NAME_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_PASSWORD_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_NUMBER_PATH).value(ExceptionResponse.NOT_BLANK));
    }

    private void assertFieldErrors(ResultActions res) throws Exception {
        final String fieldErrors = ConsUtils.FIELD_ERROR;
        res.andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.FIELD_VALIDATION_ERRORS))
                .andExpect(jsonPath(fieldErrors).isMap())
                .andExpect(jsonPath(fieldErrors, Matchers.aMapWithSize(ConsUtils.FIELD_WITH_ERRORS_AT_USER)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private ResultActions sentPostToCreateEntity(String dto, String url) throws Exception {
        return mockMvc.perform(post(url)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON));
    }
}