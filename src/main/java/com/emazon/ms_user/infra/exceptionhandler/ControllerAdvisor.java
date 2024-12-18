package com.emazon.ms_user.infra.exceptionhandler;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.infra.exception.EmailAlreadyExists;
import com.emazon.ms_user.infra.exception.UnderAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
                .body(ExceptionResponse.builder().message(ex.getMessage().split(ConsUtils.SEMI_COLON_DELIMITER)[0]).build());
    }
}
