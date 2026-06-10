package com.SocietyManagementSystem.SocietyManagementSystem_Backend.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockResponse {

    private Long blockId;
    private String blockName;
    private Integer totalBuildings;
    private String blockType;
    private String secretaryName;
}
