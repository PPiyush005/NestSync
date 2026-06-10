package com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatResponse {

    private Long flatId;
    private String flatAddress;

    private Long buildingId;
    private Integer buildingNo;

    private Long ownerId;
    private String ownerName;
}


