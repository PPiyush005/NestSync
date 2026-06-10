package com.SocietyManagementSystem.SocietyManagementSystem_Backend.banquestHall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banquet-hall")
public class BanquetHallController {

    @Autowired
    private BanquetHallService banquetHallService;

    @GetMapping("/all")
    public ResponseEntity<List<BanquetHallResponse>> getAllHalls() {
        return ResponseEntity.ok(
                banquetHallService.getAllHalls()
        );
    }

    @GetMapping("/{hallId}")
    public ResponseEntity<BanquetHallResponse> getHallById(
            @PathVariable Long hallId) {

        return ResponseEntity.ok(
                banquetHallService.getHallById(hallId)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<BanquetHallResponse> addHall(
            @RequestBody BanquetHallRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(banquetHallService.addHall(request));
    }

    @PutMapping("/update/{hallId}")
    public ResponseEntity<BanquetHallResponse> updateHall(
            @PathVariable Long hallId,
            @RequestBody BanquetHallRequest request) {

        return ResponseEntity.ok(
                banquetHallService.updateHall(hallId, request)
        );
    }

    @DeleteMapping("/delete/{hallId}")
    public ResponseEntity<String> deleteHall(
            @PathVariable Long hallId) {

        return ResponseEntity.ok(
                banquetHallService.deleteHall(hallId)
        );
    }
}
