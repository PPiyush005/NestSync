package com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner.Owner;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.worker.Worker;
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
@Table(name = "flats")
public class Flats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flatId;

    private String flatAddress;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Buildings building;

    @OneToOne(mappedBy = "flat")
    private Owner owner;

    @ManyToMany(mappedBy = "flats")
    private List<Worker> workers;
}
