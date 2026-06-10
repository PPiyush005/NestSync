package com.SocietyManagementSystem.SocietyManagementSystem_Backend.building;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingResponse {

    private Long buildingId;
    private Integer buildingNo;
    private Integer totalFlats;
    private Integer totalLift;
    private Integer totalEntry;
    private Integer totalExit;

    private Long blockId;
    private String blockName;
}
