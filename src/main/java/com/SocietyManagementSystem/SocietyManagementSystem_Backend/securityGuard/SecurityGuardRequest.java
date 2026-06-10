package com.SocietyManagementSystem.SocietyManagementSystem_Backend.securityGuard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityGuardRequest {

    private String guardName;
    private String mobileNo;
    private String address;
    private String guardType; // "HEAD_SECURITY_GUARD" or "SECURITY_GUARD"
    private Long blockId;     // required if HEAD_SECURITY_GUARD
    private Long buildingId;  // required if SECURITY_GUARD
}