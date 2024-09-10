package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.application.dto.AuthResDTO;
import com.emazon.ms_user.application.dto.RoleEnumReq;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.application.handler.IUserHandler;
import com.emazon.ms_user.domain.model.RoleEnum;
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
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserReqDTO dto, @RequestParam RoleEnumReq role) {
        handler.createUser(dto, RoleEnum.valueOf(role.name()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResDTO> login() {
        return ResponseEntity.status(HttpStatus.OK).body(handler.login());
    }
}
