package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.dto.CreateBrandDTO;
import com.senla.nn.priceservapi.dto.ViewBrandDTO;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import com.senla.nn.priceservapi.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/brands")
@Data
@RequiredArgsConstructor
@Validated
@Tag(name = "Контроллер брендов товаров", description = "Позволяет проводить операции CRUD с брендами товаров")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    @Operation(summary = "Создание бренда")
    @Secured(value = {"ROLE_ADMIN"})
    public ViewBrandDTO createBrand(@Valid @RequestBody CreateBrandDTO createBrandDTO) {
        return brandService.createBrand(createBrandDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление бренда")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> deleteBrand(
            @PathVariable @Parameter(description = "Id бренда") @Min(value = 0, message = "Id cant be negative")Long id) {
        return brandService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Список всех брендов")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public Page<ViewBrandDTO> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return brandService.findAll(pageable);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление бренда")
    @Secured(value = {"ROLE_ADMIN"})
    public ViewBrandDTO updateBrand(
            @Validated(OnUpdate.class) @PathVariable @Parameter(description = "Id бренда")
            @Min(value = 0, message = "Id cant be negative")Long id,
            @RequestBody CreateBrandDTO createBrandDTO){
        return brandService.update(id, createBrandDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение бренда по Id")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ViewBrandDTO getBrandById(
            @PathVariable @Parameter(description = "Id бренда") @Min(value = 0, message = "Id cant be negative")Long id){
        return brandService.getById(id);
    }

    @GetMapping("/name")
    @Operation(summary = "Получение бренда по названию")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ViewBrandDTO getBrandByName(@RequestParam @Parameter(description = "Название бренда") @NotBlank String name){
        return brandService.getByName(name);
    }
}
