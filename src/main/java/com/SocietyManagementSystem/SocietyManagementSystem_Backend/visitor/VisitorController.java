package com.SocietyManagementSystem.SocietyManagementSystem_Backend.visitor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/add")
    public ResponseEntity<VisitorResponse> addVisitor(@RequestBody VisitorRequest request) {
        return ResponseEntity.ok(visitorService.addVisitor(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorResponse> getVisitorById(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getVisitorById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<VisitorResponse>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.getAllVisitors());
    }

    @GetMapping("/flat/{flatId}")
    public ResponseEntity<List<VisitorResponse>> getVisitorsByFlat(@PathVariable Long flatId) {
        return ResponseEntity.ok(visitorService.getVisitorsByFlat(flatId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<VisitorResponse>> getVisitorsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(visitorService.getVisitorsByDate(date));
    }

    @GetMapping("/currently-inside")
    public ResponseEntity<List<VisitorResponse>> getCurrentlyInsideVisitors() {
        return ResponseEntity.ok(visitorService.getCurrentlyInsideVisitors());
    }

    @PatchMapping("/mark-exit/{id}")
    public ResponseEntity<VisitorResponse> markExit(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime exitTime) {
        return ResponseEntity.ok(visitorService.markExit(id, exitTime));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VisitorResponse> updateVisitor(@PathVariable Long id,
                                                         @RequestBody VisitorRequest request) {
        return ResponseEntity.ok(visitorService.updateVisitor(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVisitor(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.deleteVisitor(id));
    }
}