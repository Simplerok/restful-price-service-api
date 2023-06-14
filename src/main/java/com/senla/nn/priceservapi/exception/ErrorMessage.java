package com.senla.nn.priceservapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private String message;
    private Map<String, String> errors;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
