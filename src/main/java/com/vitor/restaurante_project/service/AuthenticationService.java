package com.vitor.restaurante_project.service;

import com.vitor.restaurante_project.config.TokenProvider;
import com.vitor.restaurante_project.database.model.RolesEntity;
import com.vitor.restaurante_project.database.model.UserEntity;
import com.vitor.restaurante_project.database.repository.RolesRepository;
import com.vitor.restaurante_project.database.repository.UserRepository;
import com.vitor.restaurante_project.dto.LoginRequestDTO;
import com.vitor.restaurante_project.dto.RegisterRequestDTO;
import com.vitor.restaurante_project.dto.TokenResponseDTO;
import com.vitor.restaurante_project.enums.RoleTypeEnums;
import com.vitor.restaurante_project.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${spring.jwt.expiration}")
    private Long expirationTime;

    public void register(RegisterRequestDTO registerRequestDTO) throws BadRequestException {
        UserEntity user = userRepository.findByEmail(registerRequestDTO.getEmail()).orElse(null);

        if(user != null){
            throw new BadRequestException("Usuário já está cadastrado!");
        }

        RolesEntity role = rolesRepository.findByNome(RoleTypeEnums.ROLE_CLIENTE.name()).orElseGet(
                () -> rolesRepository.save(RolesEntity.builder()
                        .nome(RoleTypeEnums.ROLE_CLIENTE.name())
                        .build()));

        userRepository.save(UserEntity.builder()
                .name(registerRequestDTO.getName())
                .email(registerRequestDTO.getEmail())
                .roles(Set.of(role))
                .password(passwordEncoder.encode(registerRequestDTO.getSenha()))
                .build());

    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) throws BadRequestException {
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getSenha()));
            String token = tokenProvider.generateToken(authenticate);
            return new TokenResponseDTO(token, expirationTime);
        } catch(BadCredentialsException e){
            throw new BadRequestException("Credenciais inválidas");
        }
    }
}
