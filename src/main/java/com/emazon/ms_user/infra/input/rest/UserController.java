package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.application.dto.RolesReq;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.application.handler.IUserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserHandler handler;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserReqDTO dto, @RequestParam RolesReq type) {
        handler.createUser(dto, type);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
