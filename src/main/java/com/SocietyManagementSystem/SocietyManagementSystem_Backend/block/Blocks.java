package com.SocietyManagementSystem.SocietyManagementSystem_Backend.block;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.Buildings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blocks")
public class Blocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    private String blockName;

    private Integer totalBuildings;

    private String blockType;

    private String secretaryName;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<Buildings> buildings;
}
