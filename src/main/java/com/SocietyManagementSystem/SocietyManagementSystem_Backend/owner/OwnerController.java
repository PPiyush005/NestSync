package com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/all")
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerResponse> getOwnerById(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(
                ownerService.getOwnerById(ownerId)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<OwnerResponse> addOwner(
            @RequestBody OwnerRequest ownerRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ownerService.addOwner(ownerRequest));
    }

    @PutMapping("/update/{ownerId}")
    public ResponseEntity<OwnerResponse> updateOwner(
            @PathVariable Long ownerId,
            @RequestBody OwnerRequest ownerRequest) {

        return ResponseEntity.ok(
                ownerService.updateOwner(ownerId, ownerRequest)
        );
    }

    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<String> deleteOwner(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(
                ownerService.deleteOwner(ownerId)
        );
    }
}
