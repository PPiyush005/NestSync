package com.SocietyManagementSystem.SocietyManagementSystem_Backend.block;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.Buildings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockRequest {
    private String block_name;
    private int total_building;
    private String block_type;
    private String secretary_name;
    private List<Buildings> buildings;
}
