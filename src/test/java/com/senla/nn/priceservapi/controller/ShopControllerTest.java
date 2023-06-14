package com.senla.nn.priceservapi.controller;


import com.senla.nn.priceservapi.BaseTest;
import com.senla.nn.priceservapi.dto.ShopDTO;
import com.senla.nn.priceservapi.exception.ErrorMessage;
import com.senla.nn.priceservapi.repository.ShopRepository;
import com.senla.nn.priceservapi.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShopControllerTest extends BaseTest {


    @Autowired
    private ShopServiceImpl shopService;

    @Autowired
    private ShopRepository shopRepository;


    @Test
    @DisplayName("Успешное создание магазина")
    public void shouldCreateShopSuccessfully() {
        //given
        ShopDTO shopDTO = ShopDTO.builder()
                .name("Metro")
                .build();
        HttpEntity<ShopDTO> request = new HttpEntity<>(shopDTO, getAuthHeaderForUser());

        //when
        ResponseEntity<ShopDTO> response = testRestTemplate.postForEntity("/api/v1/shops", request, ShopDTO.class);

        //then
        System.out.println(response);
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo("Metro");
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        shopService.delete(response.getBody().getId());
    }

    @Test
    @DisplayName("Попытка создания магазина с существующим именем")
    public void shouldThrowExceptionWhenCreateShop() {
        //given
        ShopDTO shopDTO = ShopDTO.builder()
                .name("Metro")
                .build();
        ShopDTO createdShop = shopService.createShop(shopDTO);
        HttpEntity<ShopDTO> request = new HttpEntity<>(shopDTO, getAuthHeaderForUser());

        //when and then
        ResponseEntity<ErrorMessage> response = testRestTemplate.postForEntity("/api/v1/shops", request, ErrorMessage.class);
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMessage());
        assertThat(response.getBody().getMessage()).isEqualTo("Shop with name=Metro is already exist");
        shopService.delete(createdShop.getId());

    }

    @Test
    public void shouldDeleteShop() {
        //given
        ShopDTO shopDTO = ShopDTO.builder()
                .name("Metro")
                .build();
        ShopDTO createdShop = shopService.createShop(shopDTO);
        Long id = createdShop.getId();
        assertThat(shopRepository.findById(id)).isPresent();
        HttpEntity<ShopDTO> request = new HttpEntity<>(getAuthHeaderForUser());

        //when
        ResponseEntity<String> response = testRestTemplate.exchange(String.format("/api/v1/shops/%s", id), HttpMethod.DELETE, request, String.class);

        //then
        assertThat(shopRepository.findById(id)).isEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
