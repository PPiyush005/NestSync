package com.SocietyManagementSystem.SocietyManagementSystem_Backend.parkingSlot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    List<ParkingSlot> findByBuilding_BuildingId(Long buildingId);

    List<ParkingSlot> findByFlat_FlatId(Long flatId);

    List<ParkingSlot> findByParkingType(ParkingType parkingType);

    List<ParkingSlot> findByStatus(ParkingStatus status);

    List<ParkingSlot> findByBuilding_BuildingIdAndParkingType(Long buildingId, ParkingType parkingType);

    List<ParkingSlot> findByBuilding_BuildingIdAndStatus(Long buildingId, ParkingStatus status);
}