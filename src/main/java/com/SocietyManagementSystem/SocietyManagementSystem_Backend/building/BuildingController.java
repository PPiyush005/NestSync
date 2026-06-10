package com.SocietyManagementSystem.SocietyManagementSystem_Backend.building;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BuildingResponse>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingResponse> getBuildingById(
            @PathVariable Long buildingId) {

        return ResponseEntity.ok(
                buildingService.getBuildingById(buildingId)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<BuildingResponse> addBuilding(
            @RequestBody BuildingRequest buildingRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildingService.addBuilding(buildingRequest));
    }

    @PutMapping("/update/{buildingId}")
    public ResponseEntity<BuildingResponse> updateBuilding(
            @PathVariable Long buildingId,
            @RequestBody BuildingRequest buildingRequest) {

        return ResponseEntity.ok(
                buildingService.updateBuilding(buildingId, buildingRequest)
        );
    }

    @DeleteMapping("/delete/{buildingId}")
    public ResponseEntity<String> deleteBuilding(
            @PathVariable Long buildingId) {

        return ResponseEntity.ok(
                buildingService.deleteBuilding(buildingId)
        );
    }
}
