package com.SocietyManagementSystem.SocietyManagementSystem_Backend.parkingSlot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlotResponse {

    private Long slotId;
    private String slotNumber;
    private ParkingType parkingType;
    private ParkingStatus status;
    private String vehicleNumber;

    // Building info
    private Long buildingId;
    private Integer buildingNo;

    // Flat info (null for VISITOR parking)
    private Long flatId;
    private String flatAddress;


}