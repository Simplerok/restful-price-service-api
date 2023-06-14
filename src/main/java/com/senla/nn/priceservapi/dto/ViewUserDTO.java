package com.senla.nn.priceservapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.nn.priceservapi.dto.validation.OnCreate;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Сущность пользователя для отображения")
public class ViewUserDTO {

    @NotNull(message = "Id must not be null")
    private Long id;

    @NotBlank(message = "Username must be not null or empty.")
    @Size(min = 3, max = 30, message = "Username should be between 3 and 30 characters.")
    private String username;

    @NotBlank(message = "Name must be not null or empty.")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters.")
    private String name;

    @NotBlank(message = "Surname must be not null or empty.")
    @Size(min = 1, max = 30, message = "Surname should be between 1 and 30 characters.")
    private String surname;

    @NotNull(message = "Birthdate must be not null.")
    @Schema(example = "1998-05-16")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Email
    private String email;

}
