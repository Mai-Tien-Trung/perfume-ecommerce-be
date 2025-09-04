package com.example.perfume_ecommerce.entity;

import com.example.perfume_ecommerce.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;   // ✅ đăng nhập bằng username

    @Column(unique = true, nullable = false)
    private String email;      // vẫn giữ email để liên lạc, verify

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

