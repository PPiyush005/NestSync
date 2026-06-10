package com.SocietyManagementSystem.SocietyManagementSystem_Backend.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // For SECRETARY — which block they manage
    private Long blockId;

    // For SECURITY_GUARD — which building they manage
    private Long buildingId;

    // For RESIDENT — which flat they belong to
    private Long flatId;
}