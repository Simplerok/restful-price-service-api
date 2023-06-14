package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.BaseTest;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserControllerTest extends BaseTest {

    @Test
    @DisplayName("Успешное добавление роли пользователю")
    public void shouldAddRoleToUser(){
        //given
        String userId = "1";
        Long roleId = 2L;
        Map<String, String> variables = new HashMap<>();
        variables.put("id", userId);
        HttpEntity<Long> request = new HttpEntity<>(roleId, getAuthHeaderForUser());

        //when
        ResponseEntity<ViewUserDTO> response = testRestTemplate
                .exchange("/api/v1/users/1/addroles", HttpMethod.PATCH, request, ViewUserDTO.class, variables);

        //then
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
