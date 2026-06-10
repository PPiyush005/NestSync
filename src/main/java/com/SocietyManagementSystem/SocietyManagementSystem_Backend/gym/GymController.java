package com.SocietyManagementSystem.SocietyManagementSystem_Backend.gym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gym")
public class GymController {

    @Autowired
    private GymService gymService;

    @GetMapping("/all")
    public ResponseEntity<List<GymResponse>> getAllGyms() {
        return ResponseEntity.ok(gymService.getAllGyms());
    }

    @GetMapping("/{gymId}")
    public ResponseEntity<GymResponse> getGymById(
            @PathVariable Long gymId) {

        return ResponseEntity.ok(
                gymService.getGymById(gymId)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<GymResponse> addGym(
            @RequestBody GymRequest gymRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gymService.addGym(gymRequest));
    }

    @PutMapping("/update/{gymId}")
    public ResponseEntity<GymResponse> updateGym(
            @PathVariable Long gymId,
            @RequestBody GymRequest gymRequest) {

        return ResponseEntity.ok(
                gymService.updateGym(gymId, gymRequest)
        );
    }

    @DeleteMapping("/delete/{gymId}")
    public ResponseEntity<String> deleteGym(
            @PathVariable Long gymId) {

        return ResponseEntity.ok(
                gymService.deleteGym(gymId)
        );
    }
}
