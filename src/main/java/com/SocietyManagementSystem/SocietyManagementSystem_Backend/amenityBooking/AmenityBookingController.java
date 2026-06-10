package com.SocietyManagementSystem.SocietyManagementSystem_Backend.amenityBooking;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/amenity-bookings")
public class AmenityBookingController {

    private final AmenityBookingService bookingService;

    public AmenityBookingController(AmenityBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/add")
    public ResponseEntity<AmenityBookingResponse> addBooking(@RequestBody AmenityBookingRequest request) {
        return ResponseEntity.ok(bookingService.addBooking(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityBookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AmenityBookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<AmenityBookingResponse>> getBookingsByAmenityType(@PathVariable String type) {
        return ResponseEntity.ok(bookingService.getBookingsByAmenityType(type));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AmenityBookingResponse>> getBookingsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AmenityBookingResponse>> getBookingsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.getBookingsByDate(date));
    }

    @GetMapping("/type/{type}/date/{date}")
    public ResponseEntity<List<AmenityBookingResponse>> getBookingsByAmenityTypeAndDate(
            @PathVariable String type,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.getBookingsByAmenityTypeAndDate(type, date));
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<AmenityBookingResponse> updateBookingStatus(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AmenityBookingResponse> updateBooking(
            @PathVariable Long id, @RequestBody AmenityBookingRequest request) {
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<AmenityBookingResponse>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }
}