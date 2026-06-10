package com.SocietyManagementSystem.SocietyManagementSystem_Backend.building;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buildings")
public class Buildings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buildingId;

    private Integer buildingNo;

    private Integer totalFlats;

    private Integer totalLift;

    private Integer totalEntry;

    private Integer totalExit;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private Blocks block;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Flats> flats;
}
