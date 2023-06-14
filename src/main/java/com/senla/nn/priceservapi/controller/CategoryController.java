package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.dto.CategoryDTO;
import com.senla.nn.priceservapi.dto.validation.OnCreate;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import com.senla.nn.priceservapi.service.CategoryService;
import com.senla.nn.priceservapi.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/v1/api/categories")
@Data
@RequiredArgsConstructor
@Validated
@Tag(name = "Контроллер категорий товаров", description = "Позволяет проводить операции CRUD с категориями товаров")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Создание категории")
    @Secured(value = {"ROLE_ADMIN"})
    public CategoryDTO createCategory(@Validated(OnCreate.class) @RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление категории")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> deleteCategory(
            @PathVariable @Parameter(description = "Id категории") @Min(value = 0, message = "Id cant be negative")Long id) {
        return categoryService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Список всех категорий")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public Page<CategoryDTO> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление категории")
    @Secured(value = {"ROLE_ADMIN"})
    public CategoryDTO updateCategory(
            @Validated(OnUpdate.class) @PathVariable @Parameter(description = "Id категории")
            @Min(value = 0, message = "Id cant be negative")Long id,
            @RequestBody CategoryDTO categoryDTO){
        return categoryService.update(id, categoryDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение категории по Id")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public CategoryDTO getCategoryById(
            @PathVariable @Parameter(description = "Id категории") @Min(value = 0, message = "Id cant be negative")Long id){
        return categoryService.getById(id);
    }

    @GetMapping("/name")
    @Operation(summary = "Получение категории по названию")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public CategoryDTO getCategoryByName(@RequestParam @Parameter(description = "Название категории") @NotBlank String name){
        return categoryService.getByName(name);
    }
}
