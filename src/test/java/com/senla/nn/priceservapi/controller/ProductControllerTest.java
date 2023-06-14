package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.BaseTest;
import com.senla.nn.priceservapi.RestPageImpl;
import com.senla.nn.priceservapi.dto.CreateProductDTO;
import com.senla.nn.priceservapi.dto.ShopDTO;
import com.senla.nn.priceservapi.dto.ViewPriceDTO;
import com.senla.nn.priceservapi.dto.ViewProductDTO;
import com.senla.nn.priceservapi.repository.ProductRepository;
import com.senla.nn.priceservapi.service.BrandService;
import com.senla.nn.priceservapi.service.CategoryService;
import com.senla.nn.priceservapi.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductControllerTest extends BaseTest {

    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("Успешное создание продукта")
    public void shouldCreateProductSuccessfully() {

        //given
        CreateProductDTO createProduct = CreateProductDTO.builder()
                .name("Сметана")
                .categoryId(3L)
                .brandId(7L)
                .build();
        HttpEntity<CreateProductDTO> request = new HttpEntity<>( createProduct, getAuthHeaderForUser());

        //when
        ResponseEntity<ViewProductDTO> response = testRestTemplate
                .postForEntity("/api/v1/products", request, ViewProductDTO.class);

        //then
        System.out.println(response);
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo("Сметана");
        assertThat(response.getBody().getBrand().getId()).isEqualTo(7L);
        assertThat(response.getBody().getCategory().getId()).isEqualTo(3L);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    @Test
    @DisplayName("Должен вернуть список всех продуктов")
    void shouldGetAllProducts() {

        //given
        long size = productRepository.findAll().size();
        HttpEntity<CreateProductDTO> request = new HttpEntity<>(getAuthHeaderForUser());

        //when
        ResponseEntity<RestPageImpl<ViewProductDTO>> response = testRestTemplate.exchange(
                "/api/v1/products", HttpMethod.GET, request, new ParameterizedTypeReference<>() {
                });

        //then
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalElements()).isEqualTo(size);

    }

    @Test
    @DisplayName("Возварт списка продуктов отфильтрованных по имени")
    void shouldGetAllProductsFilteredByName(){
        //given
        URI builder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+randomPort)
                .path("/api/v1/products")
                .queryParam("name","Творог")
                .queryParam("page",0)
                .queryParam("size", 5)
                .queryParam("sort", "id")
                .build().encode().toUri();
        HttpEntity<Object> entity = new HttpEntity<>(null,getAuthHeaderForUser());

        //when
        ResponseEntity<RestPageImpl<ViewProductDTO>> response = testRestTemplate.exchange(
                builder, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                });
        //then
        assertThat(response.getBody()).isNotNull();
        System.out.println(response);
        System.out.println(response.getBody().stream().findFirst().get().getName());
        assertThat(response.getBody().getPageable().getPageNumber()).isEqualTo(0);
        assertThat(response.getBody().getPageable().getPageSize()).isEqualTo(5);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertTrue(
                response.getBody().stream().allMatch(product -> product.getName().equals("Творог")));

    }
}