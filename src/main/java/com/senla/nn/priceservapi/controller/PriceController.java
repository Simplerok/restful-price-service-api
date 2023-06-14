package com.senla.nn.priceservapi.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.nn.priceservapi.dto.AvgPriceDTO;
import com.senla.nn.priceservapi.dto.CreatePriceDTO;
import com.senla.nn.priceservapi.dto.ShortPriceDTO;
import com.senla.nn.priceservapi.dto.ViewPriceDTO;
import com.senla.nn.priceservapi.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prices")
@Data
@RequiredArgsConstructor
@Validated
@Tag(name = "Контроллер цен на продукты", description = "Позволяет проводить операции CRUD с ценами на продукты")
public class PriceController {
    private final PriceService priceService;

    @PostMapping
    @Operation(summary = "Создание цены на продукт")
    @Secured(value = {"ROLE_ADMIN"})
    public ViewPriceDTO createPrice(@Valid @RequestBody CreatePriceDTO createPriceDTO) {
        return priceService.createPrice(createPriceDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление цены на продукт")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> deletePrice(@PathVariable @Parameter(description = "Id цены на продукт")
                                              @Min(value = 0, message = "Id cant be negative") Long id) {
        return priceService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Получение отфильтрованных цен на продукты")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public Page<ViewPriceDTO> getByFilter(
            @RequestParam(required = false) @Parameter(description = "Нименование продукта") String name,
            @RequestParam(required = false) @Parameter(description = "Id магазина") Long shopId,
            @RequestParam(required = false) @Parameter(description = "Id категории") Long categoryId,
            @RequestParam(required = false) @Parameter(description = "Id бренда") Long brandId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return priceService.getByFilter(name, shopId, categoryId, brandId, pageable);
    }

    @GetMapping("/between-date")
    @Operation(summary = "Получение динамики изменения цен в заданном периоде")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public List<ShortPriceDTO> getPricesBetweenDates(
            @RequestParam @Min(0) @Parameter(description = "Id магазина") Long shopId,
            @RequestParam @Min(0) @Parameter(description = "Id продукта") Long productId,
            @RequestParam @Parameter(description = "Дата начала", example = "27-05-2023 17:05")
            @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime from,
            @RequestParam @Parameter(description = "Дата конца", example = "27-05-2023 17:05")
            @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime to) {
        return priceService.getPricesBetweenDates(shopId, productId, from, to);
    }

    @PostMapping("/upload-excel_data")
    @Secured(value = {"ROLE_ADMIN"})
    @Operation(summary = "Создание цен на продукты из загруженного файл Excel")
    public ResponseEntity<String> createPricesFromExcel (@RequestParam("file") MultipartFile file){
        return priceService.savePricesDataFromExcel(file);
    }

    @PostMapping("/upload-csv_data")
    @Secured(value = {"ROLE_ADMIN"})
    @Operation(summary = "Создание цен на продукты из загруженного файл csv")
    public ResponseEntity<String> createPricesFromCsv (@RequestParam("file") MultipartFile file){
        return priceService.savePricesDataFromCsv(file);
    }

    @GetMapping("/avg-month")
    @Operation(summary = "Получение динамики среднего значения цен по месяцам")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public List<AvgPriceDTO> getAvgPricesGroupByMonth(
            @RequestParam @Min(0) @Parameter(description = "Id магазина") Long shopId,
            @RequestParam @Min(0) @Parameter(description = "Id продукта") Long productId,
            @RequestParam @Parameter(description = "Дата начала", example = "27-05-2023 17:05")
            @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime from,
            @RequestParam @Parameter(description = "Дата конца", example = "27-05-2023 17:05")
            @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime to) {
        return priceService.getAvgPricesGroupByMonth(shopId, productId, from, to);
    }


}
