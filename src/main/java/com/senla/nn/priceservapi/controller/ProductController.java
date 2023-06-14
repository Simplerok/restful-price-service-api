package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.dto.CreateProductDTO;
import com.senla.nn.priceservapi.dto.ViewProductDTO;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import com.senla.nn.priceservapi.service.ProductService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Data
@RequiredArgsConstructor
@Validated
@Tag(name = "Контроллер продуктов", description = "Позволяет проводить операции CRUD с продуктами")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Создание продукта")
    @Secured(value = {"ROLE_ADMIN"})
    public ViewProductDTO createShop(@Valid @RequestBody CreateProductDTO createProductDTO) {
        return productService.createProduct(createProductDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление продукта")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> deleteProduct(
            @PathVariable @Parameter(description = "Id продукта") @Min(value = 0, message = "Id cant be negative")Long id) {
        return productService.delete(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление продукта")
    @Secured(value = {"ROLE_ADMIN"})
    public ViewProductDTO updateProduct(
            @Validated(OnUpdate.class) @PathVariable @Parameter(description = "Id продукта")
            @Min(value = 0, message = "Id cant be negative")Long id,
            @RequestBody CreateProductDTO createProductDTO){
        return productService.update(id, createProductDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение продукта по Id")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ViewProductDTO getProductById(
            @PathVariable @Parameter(description = "Id продукта") @Min(value = 0, message = "Id cant be negative")Long id){
        return productService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Получение отфильтрованных продуктов")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public Page<ViewProductDTO> getByFilter(
            @RequestParam(required = false) @Parameter(description = "Нименование продукта") String name,
            @RequestParam(required = false) @Min(0) @Parameter(description = "Id категории") Long categoryId,
            @RequestParam(required = false) @Min(0) @Parameter(description = "Id бренда") Long brandId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return productService.getByFilter(name, categoryId, brandId,pageable);
    }

    @PostMapping("/upload-excel_data")
    @Operation(summary = "Создание продуктов из загруженного файла Excel")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> createProductsFromExcel (@RequestParam("file") MultipartFile file){
        return productService.saveProductsDataFromExcel(file);
    }

    @PostMapping("/upload-csv_data")
    @Operation(summary = "Создание продуктов из загруженного файла csv")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> createProductsFromCsv (@RequestParam("file") MultipartFile file){
        return productService.saveProductsDataFromCSV(file);
    }

}
