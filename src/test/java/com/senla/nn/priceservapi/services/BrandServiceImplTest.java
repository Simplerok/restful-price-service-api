package com.senla.nn.priceservapi.services;

import com.senla.nn.priceservapi.dto.CreateBrandDTO;
import com.senla.nn.priceservapi.dto.ViewBrandDTO;
import com.senla.nn.priceservapi.entity.Brand;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.mapper.BrandMapper;
import com.senla.nn.priceservapi.repository.BrandRepository;
import com.senla.nn.priceservapi.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandMapper brandMapper;
    @InjectMocks
    private BrandServiceImpl brandService;

    private static String name = "Movenpick";
    private static Brand brand = Brand.builder().name(name).build();
    private static ViewBrandDTO viewBrandDTO = ViewBrandDTO.builder()
            .id(1L)
            .name(name)
            .createdDate(LocalDate.now())
            .updatedDate(LocalDate.now()).build();

    @Test
    public void shouldCreateBrandSuccess(){
        //given

        when(brandRepository.save(brand))
                .thenReturn(Brand.builder()
                        .id(1L)
                        .name(name)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now()).build());

        //when
        when(brandMapper.toBrand(Mockito.any(CreateBrandDTO.class))).thenReturn(brand);
        when(brandMapper.toView(Mockito.any(Brand.class))).thenReturn(viewBrandDTO);
        when(brandRepository.getByName(name)).thenReturn(Optional.empty());
        ViewBrandDTO createdBrand = brandService.createBrand(CreateBrandDTO.of(name));

        //then
        Assertions.assertNotNull(createdBrand);
        Assertions.assertEquals(1L, createdBrand.getId());
        Assertions.assertEquals(name, createdBrand.getName());
        verify(brandRepository).save(brand);
    }

    @Test
    public void shouldThrowAlreadyExistsException(){
        //given and when
        when(brandRepository.getByName(name)).thenReturn(Optional.of(brand));

        //then
        Assertions.assertThrows(AlreadyExistsException.class, () -> brandService.createBrand(CreateBrandDTO.of(name)));
    }

    @Test
    void shouldDeleteBrandSuccessfully(){
        //given

        //when
        brandService.delete(1L);

        //then
        verify(brandRepository, times(1)).deleteById(1L);

    }
}
