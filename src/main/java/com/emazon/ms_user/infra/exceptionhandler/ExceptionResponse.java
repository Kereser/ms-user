package com.emazon.ms_user.infra.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    public static final String NOT_NULL = "must not be null";
    public static final String NOT_BLANK = "must not be blank";
    public static final String FIELD_VALIDATION_ERRORS = "Request has field validation errors";
    public static final String EMAIL_NOT_FOUND = "Email could not be found.";
    public static final String INVALID_TOKEN = "Invalid token.";
    public static final String BIRTH_DAY_CONSTRAINS = "Birth day doesn't meet the constrains.";

    public static final String USER_MUST_BE_OLDER = "User must be older.";
    public static final String EMAIL_MUST_BE_UNIQUE = "Email must be unique.";
    public static final String EMAIL_CONSTRAINS = "Email doesn't meet unique constrains.";

    private String message;
    private Map<String, Object> fieldErrors;
}
