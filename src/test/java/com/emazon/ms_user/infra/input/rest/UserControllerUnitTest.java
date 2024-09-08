package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.MvcUtils;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(roles="ADMIN")
class UserControllerUnitTest {

    @MockBean
    private UserHandler userHandler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    private final UserReqDTO invalidUserReq = new UserReqDTO();
    private final UserReqDTO validUserReq = UserReqDTO.builder()
            .name(ConsUtils.NAME)
            .lastName(ConsUtils.LAST_NAME)
            .password(ConsUtils.PASSWORD)
            .email(ConsUtils.EMAIL)
            .number(ConsUtils.NUMBER)
            .birthDate(ConsUtils.BIRTH_DATE_STRING)
            .idNumber(ConsUtils.ID_NUMBER)
            .build();

    private String userReqJSON;

    @Test
    void Should_Get201StatusCode_When_ValidPayload() throws Exception{
        userReqJSON = mapper.writeValueAsString(validUserReq);

        sentPostToCreateEntity(userReqJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_AUX_DEPOT_PARAM)).andExpect(status().isCreated());
    }

    @Test
    void Should_ThrowsException_When_InvalidPayloadOrMissingParam() throws Exception{
        userReqJSON = mapper.writeValueAsString(invalidUserReq);

        ResultActions res = sentPostToCreateEntity(userReqJSON, ConsUtils.BASIC_USER_URL);

        assertFieldErrors(res, ConsUtils.FIELD_WITH_ERRORS_AT_USER);
        res.andExpect(jsonPath(ConsUtils.FIELD_NAME_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_EMAIL_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_BIRTH_DATE_PATH).value(ExceptionResponse.NOT_NULL))
                .andExpect(jsonPath(ConsUtils.FIELD_ID_NUMBER_PATH).value(ExceptionResponse.NOT_NULL))
                .andExpect(jsonPath(ConsUtils.FIELD_LAST_NAME_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_PASSWORD_PATH).value(ExceptionResponse.NOT_BLANK))
                .andExpect(jsonPath(ConsUtils.FIELD_NUMBER_PATH).value(ExceptionResponse.NOT_BLANK));

        userReqJSON = mapper.writeValueAsString(validUserReq);

        sentPostToCreateEntity(userReqJSON, ConsUtils.BASIC_USER_URL)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.MISSING_REQUIRED_PARAM))
                .andExpect(jsonPath(ConsUtils.FIELD_ROLE).value(ExceptionResponse.REQUIRED_PARAM));

        sentPostToCreateEntity(userReqJSON, ConsUtils.BASIC_USER_URL, MvcUtils.buildParams(ConsUtils.TYPE_NON_VALID_PARAM))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.NOT_VALID_PARAM))
                .andExpect(jsonPath(ConsUtils.FIELD_ROLE).value(ConsUtils.TYPE_NON_VALID_PARAM.get(ConsUtils.TYPE_PARAM)));
    }

    private void assertFieldErrors(ResultActions res, Integer numberOfFields) throws Exception {
        final String fieldErrors = ConsUtils.FIELD_ERROR;
        res.andExpect(jsonPath(ConsUtils.FIELD_MESSAGE).value(ExceptionResponse.FIELD_VALIDATION_ERRORS))
                .andExpect(jsonPath(fieldErrors).isMap())
                .andExpect(jsonPath(fieldErrors, Matchers.aMapWithSize(numberOfFields)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private ResultActions sentPostToCreateEntity(String dto, String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions sentPostToCreateEntity(String dto, String url, MultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .params(params)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON));
    }
}