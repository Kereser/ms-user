package com.emazon.ms_user.infra.exceptionhandler;

import com.emazon.ms_user.infra.exception.EmailAlreadyExists;
import com.emazon.ms_user.infra.exception.UnderAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleFieldValidations(MethodArgumentNotValidException ex) {
        Map<String, Object> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));

        ExceptionResponse res = ExceptionResponse.builder()
                .message(ExceptionResponse.FIELD_VALIDATION_ERRORS)
                .fieldErrors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleNotValidReqParam(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                .message(ExceptionResponse.NOT_VALID_PARAM)
                .fieldErrors(Map.of(ex.getName(), ex.getValue() != null ? ex.getValue() : "")).build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> handleMissingRequiredParam(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                .message(ExceptionResponse.MISSING_REQUIRED_PARAM)
                .fieldErrors(Map.of(ex.getParameterName(), ExceptionResponse.REQUIRED_PARAM)).build());
    }

    @ExceptionHandler(UnderAgeException.class)
    public ResponseEntity<ExceptionResponse> handleUnderAgeClient(UnderAgeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                .message(ExceptionResponse.USER_MUST_BE_OLDER)
                .fieldErrors(Map.of(ex.getField(), ExceptionResponse.BIRTH_DAY_CONSTRAINS)).build());
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyExists(EmailAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder()
                .message(ExceptionResponse.EMAIL_CONSTRAINS)
                .fieldErrors(Map.of(ex.getField(), ExceptionResponse.EMAIL_MUST_BE_UNIQUE)).build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestOnConstrains(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder().message(ex.getMessage().split(":")[0]).build());
    }
}
