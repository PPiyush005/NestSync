package com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerResponse {

    private Long ownerId;
    private String ownerName;
    private Long ownerMobileNo;
    private String ownerProof;

    private Long flatId;
    private String flatAddress;
}
