package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.request.LoginRequest;
import com.example.perfume_ecommerce.dto.request.RegisterRequest;
import com.example.perfume_ecommerce.dto.response.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
