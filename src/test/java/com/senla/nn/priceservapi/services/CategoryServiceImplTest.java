package com.senla.nn.priceservapi.services;

import com.senla.nn.priceservapi.dto.CategoryDTO;
import com.senla.nn.priceservapi.entity.Category;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.mapper.CategoryMapper;
import com.senla.nn.priceservapi.repository.CategoryRepository;
import com.senla.nn.priceservapi.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void shouldCreateCategorySuccessfully(){
        //given
        String name = "Бытовая химимя";
        Category category = Category.builder()
                .name(name)
                .build();
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name(name)
                .build();
        when(categoryRepository.save(category))
                .thenReturn(Category.builder()
                        .id(1L)
                        .name(name)
                        .build());
        when(categoryMapper.toCategory(any(CategoryDTO.class)))
                .thenReturn(category);
        when(categoryMapper.toDTO(any(Category.class)))
                .thenReturn(categoryDTO);
        Mockito.when(categoryRepository.getByName(name)).thenReturn(Optional.empty());

        //when
        CategoryDTO createdBrand = categoryService.createCategory(CategoryDTO.builder().name(name).build());

        //then
        Assertions.assertNotNull(createdBrand);
        Assertions.assertEquals(1L, createdBrand.getId());
        Assertions.assertEquals(name, createdBrand.getName());
        Mockito.verify(categoryRepository).save(category);
    }

    @Test
    public void shouldThrowAlreadyExistsException(){
        //given
        String name = "Бытовая химимя";
        Category category = Category.builder()
                .name(name)
                .build();
        Mockito.when(categoryRepository.getByName(name)).thenReturn(Optional.of(category));


        //when and then
        Assertions.assertThrows(AlreadyExistsException.class, () -> categoryService.createCategory(CategoryDTO.builder().name(name).build()));
    }

    @Test
    void shouldFindAllCategoriesSuccessfully(){
        String name1 = "Бытовая химимя";
        String name2 = "Инструменты";
        CategoryDTO categoryDTO1 = CategoryDTO.builder()
                .id(1L)
                .name(name1)
                .build();
        CategoryDTO categoryDTO2 = CategoryDTO.builder()
                .id(2L)
                .name(name2)
                .build();
        Category category1 = Category.builder()
                .id(1L)
                .name(name1)
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .name(name2)
                .build();
        List<Category> database = List.of(category1, category2);
        Pageable pageable = Pageable.ofSize(5);
        Page<Category> pageData = new PageImpl<>(database);
        when(categoryMapper.toDTO(category1))
                .thenReturn(categoryDTO1);
        when(categoryMapper.toDTO(category2))
                .thenReturn(categoryDTO2);
        when(categoryRepository.findAll(any(Pageable.class)))
                .thenReturn(pageData);

        //when
        Page<CategoryDTO> allCategories = categoryService.findAll(pageable);

        //then
        Assertions.assertNotNull(allCategories);
        Assertions.assertEquals(2L, allCategories.getTotalElements());
        Assertions.assertEquals(1L, allCategories.getContent().get(0).getId());
        Assertions.assertEquals(2L, allCategories.getContent().get(1).getId());
        Mockito.verify(categoryRepository).findAll(pageable);

    }
}
