package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.JwtRequest;
import com.senla.nn.priceservapi.dto.JwtResponse;


public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
