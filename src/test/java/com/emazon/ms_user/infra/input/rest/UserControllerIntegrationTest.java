package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.MvcUtils;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.infra.exception_handler.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private UserReqDTO validReqUser = new UserReqDTO(
            ConsUtils.NAME,
            ConsUtils.LAST_NAME,
            ConsUtils.ID_NUMBER,
            ConsUtils.NUMBER,
            ConsUtils.BIRTH_DATE_STRING,
            ConsUtils.EMAIL,
            ConsUtils.PASSWORD);

    private String validReqUserJSON;

    @Test
    void Should_SaveAUX_DEPORT_When_ValidPayloadAndValidation() throws Exception {
        validReqUserJSON = mapper.writeValueAsString(validReqUser);
        sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM))
                .andExpect(status().isCreated());
    }

    @Test
    void Should_GetValidErrorStructure_When_EmailExistsOrUnderAge() throws Exception {
        validReqUser.setBirthDate(LocalDate.now().toString());
        validReqUserJSON = mapper.writeValueAsString(validReqUser);
        ResultActions res = sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM));

        // Birthdate error
        res.andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.USER_MUST_BE_OLDER))
                .andExpect(jsonPath(ConsUtils.FIELD_BIRTH_DATE_PATH).value(ExceptionResponse.BIRTH_DAY_CONSTRAINS));

        // Email already exists.
        validReqUser.setBirthDate(ConsUtils.BIRTH_DATE_STRING);
        validReqUserJSON = mapper.writeValueAsString(validReqUser);
        sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM)).andExpect(status().isCreated());

        sentPostToCreateEntity(validReqUserJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.EMAIL_CONSTRAINS))
                .andExpect(jsonPath(ConsUtils.FIELD_EMAIL_PATH).value(ExceptionResponse.EMAIL_MUST_BE_UNIQUE));
    }

    private ResultActions sentPostToCreateEntity(String dto, String url, MultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .params(params)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
