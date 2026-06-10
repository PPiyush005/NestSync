package com.SocietyManagementSystem.SocietyManagementSystem_Backend.parkingSlot;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.Buildings;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parking_slots")
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotId;

    private String slotNumber; // e.g. "A-01", "B-12"

    @Enumerated(EnumType.STRING)
    private ParkingType parkingType; // RESIDENT or VISITOR

    @Enumerated(EnumType.STRING)
    private ParkingStatus status; // AVAILABLE or OCCUPIED

    private String vehicleNumber; // e.g. "BR01AB1234", null if AVAILABLE

    // Always required — which building this slot belongs to
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Buildings building;

    // Only for RESIDENT parking — which flat owns this slot
    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = true)
    private Flats flat;
}