package com.senla.nn.priceservapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Сущность для создания цены на продукт")
@NoArgsConstructor
public class CreatePriceDTO {

    @Schema(description = "Цена товара", example = "150.65")
    @NotNull(message = "Price must not be null.")
    @Positive
    private BigDecimal value;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата создания цены на товар")
    private LocalDateTime createdDate;

    @NotNull(message = "Shop's ID must be not null.")
    @Schema(description = "Id магазина", example = "1")
    @Positive
    private Long shopId;

    @NotNull(message = "Product's ID must be not null.")
    @Schema(description = "Id товара", example = "1")
    @Positive
    private Long productId;
}
