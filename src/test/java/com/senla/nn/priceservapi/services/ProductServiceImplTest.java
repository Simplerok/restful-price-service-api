package com.senla.nn.priceservapi.services;

import com.senla.nn.priceservapi.dto.CreateProductDTO;
import com.senla.nn.priceservapi.dto.ViewProductDTO;
import com.senla.nn.priceservapi.entity.Brand;
import com.senla.nn.priceservapi.entity.Category;
import com.senla.nn.priceservapi.entity.Product;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.mapper.ProductMapper;
import com.senla.nn.priceservapi.repository.BrandRepository;
import com.senla.nn.priceservapi.repository.CategoryRepository;
import com.senla.nn.priceservapi.repository.ProductRepository;
import com.senla.nn.priceservapi.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    private static Category category = Category.builder().id(1L).name("Хлеб").build();
    private static Brand brand = Brand.builder().id(1L)
            .name("Каравай")
            .createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();
    private static String productName = "Хлеб черный";
    private static Product rawProduct = Product.builder()
            .name(productName)
            .brand(brand)
            .category(category)
            .build();
    private static ViewProductDTO viewProduct = ViewProductDTO.builder()
            .id(1L)
            .name(productName)
            .createdDate(LocalDateTime.now())
            .updatedDate(LocalDateTime.now())
            .brand(brand)
            .category(category)
            .build();

    @Test
    void shouldCreateProductSuccessfully(){
        //given and when
        when(productRepository.save(rawProduct))
                .thenReturn(Product.builder()
                        .id(1L)
                        .name(productName)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .brand(brand)
                        .category(category)
                        .build());
        when(productMapper.toView(any(Product.class)))
                .thenReturn(viewProduct);
        Mockito.when(productRepository.existsByNameAndBrandId(productName, brand.getId())).thenReturn(true);
        Mockito.when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        ViewProductDTO createdProduct = productService.createProduct(CreateProductDTO.builder()
                .name(productName)
                .brandId(brand.getId())
                .categoryId(category.getId())
                .build());

        //then
        Assertions.assertNotNull(createdProduct);
        Assertions.assertEquals(1L, createdProduct.getId());
        Assertions.assertEquals(productName, createdProduct.getName());
        Mockito.verify(productRepository).save(rawProduct);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCreateProduct(){
        //given and when
        Mockito.when(productRepository.existsByNameAndBrandId(productName, brand.getId())).thenReturn(true);
        Mockito.when(brandRepository.findById(brand.getId())).thenThrow(NotFoundException.class);


        //then
        Assertions.assertThrows(NotFoundException.class, () -> productService.createProduct(CreateProductDTO.builder()
                .name(productName)
                .brandId(brand.getId())
                .categoryId(category.getId())
                .build()));

    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenCreateProduct(){
        //given and when
        Mockito.when(productRepository.existsByNameAndBrandId(productName, brand.getId())).thenReturn(false);


        //then
        Assertions.assertThrows(AlreadyExistsException.class, () -> productService.createProduct(CreateProductDTO.builder()
                .name(productName)
                .brandId(brand.getId())
                .categoryId(category.getId())
                .build()));

    }

}
