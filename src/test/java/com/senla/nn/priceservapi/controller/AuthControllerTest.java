package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.BaseTest;
import com.senla.nn.priceservapi.dto.CreateUserDTO;
import com.senla.nn.priceservapi.dto.JwtRequest;
import com.senla.nn.priceservapi.dto.JwtResponse;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import com.senla.nn.priceservapi.exception.ErrorMessage;
import com.senla.nn.priceservapi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthControllerTest extends BaseTest {

    @Autowired
    UserService userService;


    @Test
    @DisplayName("Успешная авторизация пользователей")
    public void shouldLoginSuccessfully(){
        //given
        String username = "RikiTikiTak";
        String password = "PoIu963:?0520";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<JwtRequest> requestHttpEntity = new HttpEntity<>(request, headers);

        //when
        ResponseEntity<JwtResponse> response = testRestTemplate
                .postForEntity("/api/v1/auth/login", requestHttpEntity, JwtResponse.class);

        //then
        System.out.println(response.getBody());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo(username);
        assertThat(response.getBody().getAccessToken()).isNotBlank();

    }

    @Test
    @DisplayName("Ошибка при авторизации")
    public void shouldLThrowExceptionWhenLogin(){
        //given
        String username = "RikiTikiTak";
        String password = "QwE123!.23";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<JwtRequest> requestHttpEntity = new HttpEntity<>(request, headers);

        //when
        ResponseEntity<ErrorMessage> response = testRestTemplate
                .postForEntity("/api/v1/auth/login", requestHttpEntity, ErrorMessage.class);

        //then
        System.out.println(response);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Authentication failed.");


    }

    @Test
    @DisplayName("Успешная регистрация пользователей")
    void shouldRegisterUserSuccessfully() throws Exception {
        //given
        CreateUserDTO createdUser = CreateUserDTO.builder()
                .username("Ganza25")
                .name("Макар")
                .surname("Макаревич")
                .birthDate(LocalDate.of(1985,5,14))
                .email("Makariy145@gmail.com")
                .password("QwE123.!23")
                .build();

        //when
        ResponseEntity<ViewUserDTO> response = testRestTemplate.postForEntity("/api/v1/auth/register", createdUser, ViewUserDTO.class);

        //then
        System.out.println(response);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo("Ganza25");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Ошибка при создании пользователя")
    void shouldThrowExceptionWhenCreateUser(){
        //given
        ViewUserDTO existedUser = userService.getById(1L);
        CreateUserDTO createdUser = CreateUserDTO.builder()
                .username(existedUser.getUsername())
                .name(existedUser.getName())
                .surname(existedUser.getSurname())
                .birthDate(existedUser.getBirthDate())
                .email(existedUser.getEmail())
                .password("QwE123.!23")
                .build();

        //when
        ResponseEntity<ErrorMessage> response = testRestTemplate
                .postForEntity("/api/v1/auth/register", createdUser, ErrorMessage.class);

        //then
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMessage());
        assertThat(response.getBody().getMessage())
                .isEqualTo(String.format("User with  username=%s is already exist", createdUser.getUsername()));
    }
}