package com.SocietyManagementSystem.SocietyManagementSystem_Backend.parkingSlot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSlotRequest {

    private String slotNumber;
    private String parkingType;   // "RESIDENT" or "VISITOR"
    private String vehicleNumber; // optional
    private Long buildingId;      // always required
    private Long flatId;          // required only for RESIDENT
}