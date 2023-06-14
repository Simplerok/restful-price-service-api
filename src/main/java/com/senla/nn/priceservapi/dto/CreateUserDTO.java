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
@Schema(description = "Сущность пользователя")
public class CreateUserDTO {

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

    @Email(groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @Pattern(regexp = "((?=.*\\d)(?=.*[a-zA-Z])(?=.*\\p{Punct}).{6,20})",
            message = "The password must have one capital letter, one digit and one special character. " +
                      "The password length must not be less than 6 characters",
            groups = {OnCreate.class, OnUpdate.class})
    @Schema(example = "Qwerty_123.")
    private String password;
}
