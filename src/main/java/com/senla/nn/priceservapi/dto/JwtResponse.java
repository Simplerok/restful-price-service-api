package com.senla.nn.priceservapi.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



@Data
public class JwtResponse {

    @Schema(description = "Id пользователя")
    private Long id;
    @Schema(description = "Логин пользователя")
    private String username;
    @Schema(description = "Токен доступа")
    private String accessToken;
    @Schema(description = "Токен для обновления токена доступа")
    private String refreshToken;
}
