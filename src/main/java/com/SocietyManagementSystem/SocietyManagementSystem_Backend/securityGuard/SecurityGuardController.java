package com.SocietyManagementSystem.SocietyManagementSystem_Backend.securityGuard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-guards")
public class SecurityGuardController {

    private final SecurityGuardService securityGuardService;

    public SecurityGuardController(SecurityGuardService securityGuardService) {
        this.securityGuardService = securityGuardService;
    }

    @PostMapping("/add")
    public ResponseEntity<SecurityGuardResponse> addGuard(@RequestBody SecurityGuardRequest request) {
        return ResponseEntity.ok(securityGuardService.addGuard(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityGuardResponse> getGuardById(@PathVariable Long id) {
        return ResponseEntity.ok(securityGuardService.getGuardById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SecurityGuardResponse>> getAllGuards() {
        return ResponseEntity.ok(securityGuardService.getAllGuards());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<SecurityGuardResponse>> getGuardsByType(@PathVariable String type) {
        return ResponseEntity.ok(securityGuardService.getGuardsByType(type));
    }

    @GetMapping("/block/{blockId}")
    public ResponseEntity<List<SecurityGuardResponse>> getGuardsByBlock(@PathVariable Long blockId) {
        return ResponseEntity.ok(securityGuardService.getGuardsByBlock(blockId));
    }

    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<SecurityGuardResponse>> getGuardsByBuilding(@PathVariable Long buildingId) {
        return ResponseEntity.ok(securityGuardService.getGuardsByBuilding(buildingId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SecurityGuardResponse> updateGuard(@PathVariable Long id,
                                                             @RequestBody SecurityGuardRequest request) {
        return ResponseEntity.ok(securityGuardService.updateGuard(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGuard(@PathVariable Long id) {
        return ResponseEntity.ok(securityGuardService.deleteGuard(id));
    }
}