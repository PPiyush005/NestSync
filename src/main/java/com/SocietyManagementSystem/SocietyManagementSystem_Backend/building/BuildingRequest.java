package com.SocietyManagementSystem.SocietyManagementSystem_Backend.building;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequest {

    private Integer buildingNo;

    private Integer totalFlats;

    private Integer totalLift;

    private Integer totalEntry;

    private Integer totalExit;

    private Long blockId;
}
