package com.vitor.restaurante_project.controller;

import com.vitor.restaurante_project.dto.LoginRequestDTO;
import com.vitor.restaurante_project.dto.RegisterRequestDTO;
import com.vitor.restaurante_project.dto.TokenResponseDTO;
import com.vitor.restaurante_project.exception.BadRequestException;
import com.vitor.restaurante_project.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) throws BadRequestException {
        authenticationService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws Exception {
        return authenticationService.login(loginRequestDTO);
    }
}
