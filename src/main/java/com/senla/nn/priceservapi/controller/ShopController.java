package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.dto.ShopDTO;
import com.senla.nn.priceservapi.dto.validation.OnCreate;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import com.senla.nn.priceservapi.service.ShopService;
import com.senla.nn.priceservapi.service.impl.ShopServiceImpl;
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
@RequestMapping("/api/v1/shops")
@Data
@RequiredArgsConstructor
@Validated
@Tag(name = "Контроллер магазинов", description = "Позволяет проводить операции CRUD с магазинами")
public class ShopController {
    private final ShopService shopService;

    @PostMapping
    @Operation(summary = "Создание магазина")
    @Secured(value = {"ROLE_ADMIN"})
    public ShopDTO createShop(@Validated(OnCreate.class) @RequestBody ShopDTO shopDTO) {
        return shopService.createShop(shopDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление магазина")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> deleteShop(
            @PathVariable @Parameter(description = "Id магазина") @Min(value = 0, message = "Id cant be negative")Long id) {
        return shopService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Список всех магазинов")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public Page<ShopDTO> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return shopService.findAll(pageable);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление магазина")
    @Secured(value = {"ROLE_ADMIN"})
    public ShopDTO updateShop(
            @Validated(OnUpdate.class) @PathVariable @Parameter(description = "Id магазина")
            @Min(value = 0, message = "Id cant be negative")Long id,
            @RequestBody ShopDTO shopDTO){
        return shopService.update(id, shopDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение магазина по Id")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ShopDTO getShopById(
            @PathVariable @Parameter(description = "Id магазина") @Min(value = 0, message = "Id cant be negative")Long id){
        return shopService.getById(id);
    }

    @GetMapping("/name")
    @Operation(summary = "Получение магазина по названию")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ShopDTO getShopByName(@RequestParam @Parameter(description = "Название магазина") @NotBlank String name){
        return shopService.getByName(name);
    }
}
