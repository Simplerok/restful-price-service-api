package com.senla.nn.priceservapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.senla.nn.priceservapi.dto.JwtRequest;
import com.senla.nn.priceservapi.dto.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    @LocalServerPort
    protected int randomPort;
    @Autowired
    protected TestRestTemplate testRestTemplate;

    public ObjectMapper objectMapper;

    protected HttpHeaders getAuthHeaderForUser(){
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
        assertThat(response.getBody()).isNotNull();
        headers.add("Authorization", "Bearer " + response.getBody().getAccessToken());

        return headers;
    }
    public BaseTest(){
        objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
    }
}
