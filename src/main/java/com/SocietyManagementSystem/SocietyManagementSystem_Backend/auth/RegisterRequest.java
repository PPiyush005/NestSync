package com.SocietyManagementSystem.SocietyManagementSystem_Backend.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String role;       // ADMIN, SECRETARY, RESIDENT, SECURITY_GUARD
    private Long blockId;      // for SECRETARY
    private Long buildingId;   // for SECURITY_GUARD
    private Long flatId;       // for RESIDENT
}