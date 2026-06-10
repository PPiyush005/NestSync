package com.SocietyManagementSystem.SocietyManagementSystem_Backend.amenityBooking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "amenity_bookings")
public class AmenityBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String bookedByName;

    private String bookedByMobile;

    @Enumerated(EnumType.STRING)
    private AmenityType amenityType; // GYM or BANQUET_HALL

    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String bookedByUsername;

    @Column(length = 500)
    private String purpose; // optional note

    @Enumerated(EnumType.STRING)
    private BookingStatus status; // PENDING, CONFIRMED, CANCELLED
}