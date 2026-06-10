package com.SocietyManagementSystem.SocietyManagementSystem_Backend.parkingSlot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @PostMapping("/add")
    public ResponseEntity<ParkingSlotResponse> addSlot(@RequestBody ParkingSlotRequest request) {
        return ResponseEntity.ok(parkingSlotService.addSlot(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlotResponse> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSlotService.getSlotById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ParkingSlotResponse>> getAllSlots() {
        return ResponseEntity.ok(parkingSlotService.getAllSlots());
    }

    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<ParkingSlotResponse>> getSlotsByBuilding(@PathVariable Long buildingId) {
        return ResponseEntity.ok(parkingSlotService.getSlotsByBuilding(buildingId));
    }

    @GetMapping("/flat/{flatId}")
    public ResponseEntity<List<ParkingSlotResponse>> getSlotsByFlat(@PathVariable Long flatId) {
        return ResponseEntity.ok(parkingSlotService.getSlotsByFlat(flatId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ParkingSlotResponse>> getSlotsByType(@PathVariable String type) {
        return ResponseEntity.ok(parkingSlotService.getSlotsByType(type));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ParkingSlotResponse>> getSlotsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(parkingSlotService.getSlotsByStatus(status));
    }

    @GetMapping("/building/{buildingId}/type/{type}")
    public ResponseEntity<List<ParkingSlotResponse>> getSlotsByBuildingAndType(
            @PathVariable Long buildingId, @PathVariable String type) {
        return ResponseEntity.ok(parkingSlotService.getSlotsByBuildingAndType(buildingId, type));
    }

    @GetMapping("/building/{buildingId}/available")
    public ResponseEntity<List<ParkingSlotResponse>> getAvailableSlotsByBuilding(@PathVariable Long buildingId) {
        return ResponseEntity.ok(parkingSlotService.getAvailableSlotsByBuilding(buildingId));
    }

    @PatchMapping("/assign-vehicle/{slotId}")
    public ResponseEntity<ParkingSlotResponse> assignVehicle(
            @PathVariable Long slotId, @RequestParam String vehicleNumber) {
        return ResponseEntity.ok(parkingSlotService.assignVehicle(slotId, vehicleNumber));
    }

    @PatchMapping("/remove-vehicle/{slotId}")
    public ResponseEntity<ParkingSlotResponse> removeVehicle(@PathVariable Long slotId) {
        return ResponseEntity.ok(parkingSlotService.removeVehicle(slotId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ParkingSlotResponse> updateSlot(@PathVariable Long id,
                                                          @RequestBody ParkingSlotRequest request) {
        return ResponseEntity.ok(parkingSlotService.updateSlot(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSlotService.deleteSlot(id));
    }
}