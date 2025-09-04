package com.example.perfume_ecommerce.service.imp;

import com.example.perfume_ecommerce.security.JwtService;
import com.example.perfume_ecommerce.dto.request.LoginRequest;
import com.example.perfume_ecommerce.dto.request.RegisterRequest;
import com.example.perfume_ecommerce.dto.response.AuthResponse;
import com.example.perfume_ecommerce.entity.User;
import com.example.perfume_ecommerce.repository.UserRepository;
import com.example.perfume_ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // class tự viết để generate/validate JWT

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        userRepository.save(user);
    }
    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
