package com.senla.nn.priceservapi.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.nn.priceservapi.dto.validation.OnCreate;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class JwtRequest {

    @NotNull(message = "Username must be not null.")
    @Size(min = 4, max = 30, message = "Username should be between 4 and 30 characters.")
    @Schema(description = "Логин пользователя")
    private String username;

    @Pattern(regexp = "((?=.*\\d)(?=.*[a-zA-Z])(?=.*\\p{Punct}).{6,20})",
            message = "The password must have one capital letter, one digit and one special character. " +
                    "The password length must not be less than 6 characters",
            groups = {OnCreate.class, OnUpdate.class})
    @Schema(description="Пароль",example = "Qwerty_123.")
    private String password;
}
