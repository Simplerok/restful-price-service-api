package com.senla.nn.priceservapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Сущность для создания продукта")
@NoArgsConstructor
public class CreateProductDTO {

    @NotBlank(message = "Name must be not null or empty.")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters.")
    @Schema(description = "Наименование товара")
    private String name;

    @NotNull(message = "Brand's ID must be not null.")
    @Schema(description = "Id бренда товара", example = "1")
    @Positive
    private Long brandId;

    @NotNull(message = "Category's ID must be not null.")
    @Schema(description = "Id категории товара", example = "1")
    @Positive
    private Long categoryId;

}
