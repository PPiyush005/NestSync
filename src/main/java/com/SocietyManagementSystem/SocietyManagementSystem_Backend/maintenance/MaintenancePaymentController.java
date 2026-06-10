package com.SocietyManagementSystem.SocietyManagementSystem_Backend.maintenance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenancePaymentController {

    private final MaintenancePaymentService paymentService;

    public MaintenancePaymentController(MaintenancePaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    public ResponseEntity<MaintenancePaymentResponse> addPayment(
            @RequestBody MaintenancePaymentRequest request) {
        return ResponseEntity.ok(paymentService.addPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenancePaymentResponse> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MaintenancePaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/flat/{flatId}")
    public ResponseEntity<List<MaintenancePaymentResponse>> getPaymentsByFlat(
            @PathVariable Long flatId) {
        return ResponseEntity.ok(paymentService.getPaymentsByFlat(flatId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenancePaymentResponse>> getPaymentsByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<List<MaintenancePaymentResponse>> getPaymentsByMonthAndYear(
            @PathVariable Integer month, @PathVariable Integer year) {
        return ResponseEntity.ok(paymentService.getPaymentsByMonthAndYear(month, year));
    }

    @GetMapping("/flat/{flatId}/status/{status}")
    public ResponseEntity<List<MaintenancePaymentResponse>> getPaymentsByFlatAndStatus(
            @PathVariable Long flatId, @PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByFlatAndStatus(flatId, status));
    }

    @PatchMapping("/mark-paid/{id}")
    public ResponseEntity<MaintenancePaymentResponse> markAsPaid(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.markAsPaid(id));
    }

    @PatchMapping("/mark-overdue/{id}")
    public ResponseEntity<MaintenancePaymentResponse> markAsOverdue(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.markAsOverdue(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MaintenancePaymentResponse> updatePayment(
            @PathVariable Long id, @RequestBody MaintenancePaymentRequest request) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }

    @GetMapping("/my-bills")
    public ResponseEntity<List<MaintenancePaymentResponse>> getMyBills() {
        return ResponseEntity.ok(paymentService.getMyBills());
    }
}