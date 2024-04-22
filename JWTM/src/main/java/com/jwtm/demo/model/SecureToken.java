package com.jwtm.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "secure_tokens")
public class SecureToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expirationTime;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}