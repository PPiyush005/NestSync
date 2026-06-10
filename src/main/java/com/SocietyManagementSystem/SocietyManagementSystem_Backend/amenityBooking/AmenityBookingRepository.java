package com.SocietyManagementSystem.SocietyManagementSystem_Backend.amenityBooking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AmenityBookingRepository extends JpaRepository<AmenityBooking, Long> {

    List<AmenityBooking> findByAmenityType(AmenityType amenityType);

    List<AmenityBooking> findByStatus(BookingStatus status);

    List<AmenityBooking> findByBookingDate(LocalDate date);

    List<AmenityBooking> findByAmenityTypeAndBookingDate(AmenityType amenityType, LocalDate date);

    List<AmenityBooking> findByBookedByUsername(String username);

    // Conflict detection — find overlapping bookings for same amenity on same date
    // excludes CANCELLED bookings
    @Query("SELECT b FROM AmenityBooking b WHERE b.amenityType = :amenityType " +
            "AND b.bookingDate = :bookingDate " +
            "AND b.status <> 'CANCELLED' " +
            "AND b.startTime < :endTime " +
            "AND b.endTime > :startTime")
    List<AmenityBooking> findConflictingBookings(
            @Param("amenityType") AmenityType amenityType,
            @Param("bookingDate") LocalDate bookingDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}