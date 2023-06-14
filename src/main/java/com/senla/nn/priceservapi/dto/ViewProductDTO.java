package com.senla.nn.priceservapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.senla.nn.priceservapi.entity.Brand;
import com.senla.nn.priceservapi.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Сущность продукта для отображения")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewProductDTO {
    @Schema(description = "Id продукта")
    private Long id;

    @Schema(description = "Наименование продукта")
    private String name;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize
    @Schema(description = "Дата создания продукта")
    private LocalDateTime createdDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата обновления продукта")
    private LocalDateTime updatedDate;

    @Schema(description = "Бренд товара")
    private Brand brand;

    @Schema(description = "Категория товара")
    private Category category;

}
