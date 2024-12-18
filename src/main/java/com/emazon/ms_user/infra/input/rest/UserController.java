package com.emazon.ms_user.infra.input.rest;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.application.dto.AuthResDTO;
import com.emazon.ms_user.application.dto.UserReqDTO;
import com.emazon.ms_user.application.handler.IUserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConsUtils.BASIC_USER_URL)
@RequiredArgsConstructor
public class UserController {

    private final IUserHandler handler;

    @PostMapping(ConsUtils.AUX_DEPOT_URL)
    public ResponseEntity<Void> createAuxDepotUser(@RequestBody @Valid UserReqDTO dto) {
        handler.createAuxDepotUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(ConsUtils.CLIENT_URL)
    public ResponseEntity<Void> createUserClient(@RequestBody @Valid UserReqDTO dto) {
        handler.createClientUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(ConsUtils.LOGIN_URL)
    public ResponseEntity<AuthResDTO> login() {
        return ResponseEntity.status(HttpStatus.OK).body(handler.login());
    }
}
