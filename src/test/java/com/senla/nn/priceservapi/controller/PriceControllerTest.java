package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.BaseTest;
import com.senla.nn.priceservapi.RestPageImpl;
import com.senla.nn.priceservapi.dto.AvgPriceDTO;
import com.senla.nn.priceservapi.dto.CreatePriceDTO;
import com.senla.nn.priceservapi.dto.ViewPriceDTO;
import com.senla.nn.priceservapi.repository.PriceRepository;
import com.senla.nn.priceservapi.service.PriceService;
import com.senla.nn.priceservapi.service.ProductService;
import com.senla.nn.priceservapi.service.ShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PriceControllerTest extends BaseTest {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PriceService priceService;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    @DisplayName("Возварт списка цен на все продукты")
    void shouldGetAllPrices(){
        //given
        long size = priceRepository.findAll().size();
        HttpEntity<Object> entity = new HttpEntity<>(null,getAuthHeaderForUser());

        //when
        ResponseEntity<RestPageImpl<ViewPriceDTO>> response = testRestTemplate.exchange(
                "/api/v1/prices", HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                });
        //then
        assertThat(response.getBody()).isNotNull();
        System.out.println(response);
        System.out.println(response.getBody().stream().findFirst().get().getValue());
        assertThat(response.getBody().getPageable().getPageNumber()).isEqualTo(0);
        assertThat(response.getBody().getPageable().getPageSize()).isEqualTo(5);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTotalElements()).isEqualTo(size);
    }

    @Test
    @DisplayName("Возварт списка цен на все продукты отфильтрованный по имени")
    void shouldGetAllPricesFilteredByName(){
        //given
        URI builder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+randomPort)
                .path("/api/v1/prices")
                .queryParam("name","Хлеб бородинский")
                .queryParam("page",0)
                .queryParam("size", 5)
                .queryParam("sort", "id")
                .build().encode().toUri();
        HttpEntity<Object> entity = new HttpEntity<>(null,getAuthHeaderForUser());

        //when
        ResponseEntity<RestPageImpl<ViewPriceDTO>> response = testRestTemplate.exchange(
                builder, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                });
        //then
        assertThat(response.getBody()).isNotNull();
        System.out.println(response);
        System.out.println(response.getBody().stream().findFirst().get().getProduct().getName());
        assertThat(response.getBody().getPageable().getPageNumber()).isEqualTo(0);
        assertThat(response.getBody().getPageable().getPageSize()).isEqualTo(5);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().stream().findFirst().get().getProduct().getName()).isEqualTo("Хлеб бородинский");
        Assertions.assertTrue(
                response.getBody().stream().allMatch(price -> price.getProduct().getName().equals("Хлеб бородинский")));

    }

    @Test
    @DisplayName("Отображение средней цены на продукты по месяцам")
    void shouldGetAvgPricesGroupByMonth(){

        //given
        Long shopId = shopService.getByName("Магнит").getId();
        Long productId = productService
                .getByFilter("Хлеб бородинский",1L,1L, Pageable.ofSize(1))
                .getContent().get(0).getId();
        CreatePriceDTO price1 = CreatePriceDTO.builder()
                .value(BigDecimal.valueOf(32.45))
                .createdDate(LocalDateTime.of(2023, 1, 6,14,25,38))
                .shopId(shopId)
                .productId(productId)
                .build();
        CreatePriceDTO price2 = CreatePriceDTO.builder()
                .value(BigDecimal.valueOf(35.45))
                .createdDate(LocalDateTime.of(2023, 1, 16,14,25,38))
                .shopId(shopId)
                .productId(productId)
                .build();
        CreatePriceDTO price3 = CreatePriceDTO.builder()
                .value(BigDecimal.valueOf(31.45))
                .createdDate(LocalDateTime.of(2023, 3, 12,14,25,38))
                .shopId(shopId)
                .productId(productId)
                .build();
        CreatePriceDTO price4 = CreatePriceDTO.builder()
                .value(BigDecimal.valueOf(39.35))
                .createdDate(LocalDateTime.of(2023, 3, 23,14,25,38))
                .shopId(shopId)
                .productId(productId)
                .build();
        ViewPriceDTO priceDTO1 = priceService.createPrice(price1);
        priceService.createPrice(price2);
        priceService.createPrice(price3);
        priceService.createPrice(price4);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+randomPort)
                .path("/api/v1/prices/avg-month")
                .queryParam("shopId",shopId)
                .queryParam("productId", productId)
                .queryParam("from", "24-12-2022 17:05")
                .queryParam("to", "24-06-2023 17:05")
                .build().encode().toUri();
        HttpEntity<CreatePriceDTO> request = new HttpEntity<>(null,getAuthHeaderForUser());

        //when
        ResponseEntity<List<AvgPriceDTO>> response = testRestTemplate.exchange(
                uri, HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
        System.out.println(response);

        //then
        System.out.println(response.getBody().stream().findFirst());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(0).getValue()).isNotZero();

    }
}
