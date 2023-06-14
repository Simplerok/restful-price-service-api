package com.senla.nn.priceservapi.dto;


import com.senla.nn.priceservapi.dto.validation.OnCreate;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Сущность магазина")
public class ShopDTO {

    @Null(groups = OnCreate.class)
    @NotNull(message = "Id must not be null", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "Name must be not null or empty.", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters.")
    private String name;
}
