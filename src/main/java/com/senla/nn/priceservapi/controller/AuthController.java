package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.dto.CreateUserDTO;
import com.senla.nn.priceservapi.dto.JwtRequest;
import com.senla.nn.priceservapi.dto.JwtResponse;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import com.senla.nn.priceservapi.dto.validation.OnCreate;
import com.senla.nn.priceservapi.service.AuthService;
import com.senla.nn.priceservapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name="Контроллер регистрации и авторизации", description = "Позволяет регистрировать и авторизировать пользователей")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping(value = "/login")
    @Operation(summary = "Авторизация пользователя")
    public JwtResponse login(@Valid @RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ViewUserDTO registerUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        return userService.createUser(createUserDTO);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновление токена")
    public JwtResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }
}
