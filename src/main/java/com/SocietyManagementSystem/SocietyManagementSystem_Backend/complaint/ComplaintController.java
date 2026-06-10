package com.SocietyManagementSystem.SocietyManagementSystem_Backend.complaint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/add")
    public ResponseEntity<ComplaintResponse> addComplaint(@RequestBody ComplaintRequest request) {
        return ResponseEntity.ok(complaintService.addComplaint(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ComplaintResponse>> getComplaintsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(complaintService.getComplaintsByStatus(status));
    }

    @GetMapping("/flat/{flatId}")
    public ResponseEntity<List<ComplaintResponse>> getComplaintsByFlat(@PathVariable Long flatId) {
        return ResponseEntity.ok(complaintService.getComplaintsByFlat(flatId));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ComplaintResponse>> getComplaintsByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(complaintService.getComplaintsByOwner(ownerId));
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<ComplaintResponse> updateComplaintStatus(@PathVariable Long id,
                                                                   @RequestParam String status) {
        return ResponseEntity.ok(complaintService.updateComplaintStatus(id, status));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ComplaintResponse> updateComplaint(@PathVariable Long id,
                                                             @RequestBody ComplaintRequest request) {
        return ResponseEntity.ok(complaintService.updateComplaint(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.deleteComplaint(id));
    }

    @GetMapping("/my-complaints")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints() {
        return ResponseEntity.ok(complaintService.getMyComplaints());
    }
}