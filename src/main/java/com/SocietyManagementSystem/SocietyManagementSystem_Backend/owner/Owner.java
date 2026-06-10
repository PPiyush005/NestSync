package com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;

    private String ownerName;

    private Long ownerMobileNo;

    private String ownerProof;

    @OneToOne
    @JoinColumn(name = "flat_id")
    private Flats flat;
}
