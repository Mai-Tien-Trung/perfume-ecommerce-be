package com.example.perfume_ecommerce.model;

import com.example.perfume_ecommerce.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    // getters/setters
}
