package com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flat")
public class FlatController {

    @Autowired
    private FlatService flatService;

    @GetMapping("/all")
    public ResponseEntity<List<FlatResponse>> getAllFlats() {
        return ResponseEntity.ok(flatService.getAllFlats());
    }

    @GetMapping("/{flatId}")
    public ResponseEntity<FlatResponse> getFlatById(
            @PathVariable String flatId) {

        return ResponseEntity.ok(
                flatService.getFlatById(flatId)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<FlatResponse> addFlat(
            @RequestBody FlatRequest flatRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flatService.addFlat(flatRequest));
    }

    @PutMapping("/update/{flatId}")
    public ResponseEntity<FlatResponse> updateFlat(
            @PathVariable String flatId,
            @RequestBody FlatRequest flatRequest) {

        return ResponseEntity.ok(
                flatService.updateFlat(flatId, flatRequest)
        );
    }

    @DeleteMapping("/delete/{flatId}")
    public ResponseEntity<String> deleteFlat(
            @PathVariable String flatId) {

        return ResponseEntity.ok(
                flatService.deleteFlat(flatId)
        );
    }
}
