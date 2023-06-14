package com.senla.nn.priceservapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.nn.priceservapi.dto.validation.OnCreate;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@Schema(description = "Сущность бренда для создания")
public class CreateBrandDTO {

    @NotBlank(message = "Name must be not null or empty.")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters.")
    @Schema(description = "Название бренда")
    private String name;

}
