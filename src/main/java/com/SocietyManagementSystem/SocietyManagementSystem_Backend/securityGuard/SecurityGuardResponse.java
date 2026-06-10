package com.SocietyManagementSystem.SocietyManagementSystem_Backend.securityGuard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityGuardResponse {

    private Long guardId;
    private String guardName;
    private String mobileNo;
    private String address;
    private GuardType guardType;

    // Block info (for HEAD_SECURITY_GUARD)
    private Long blockId;
    private String blockName;

    // Building info (for SECURITY_GUARD)
    private Long buildingId;
    private Integer buildingNo;


}