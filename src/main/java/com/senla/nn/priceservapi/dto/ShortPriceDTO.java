package com.senla.nn.priceservapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.nn.priceservapi.entity.Product;
import com.senla.nn.priceservapi.entity.Shop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Сущность цены продукта для отображения(урезанная)")
public class ShortPriceDTO {

    @Schema(description = "Значение цены на товар")
    private BigDecimal value;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата создания цены на товар")
    private LocalDateTime createdDate;

    @Schema(description = "Id магазина в котором задана цена на товар")
    private Long shopId;

    @Schema(description = "Id товара")
    private Long productId;

}
