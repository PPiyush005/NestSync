package com.SocietyManagementSystem.SocietyManagementSystem_Backend.amenityBooking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenityBookingResponse {

    private Long bookingId;
    private String bookedByName;
    private String bookedByMobile;
    private AmenityType amenityType;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;
    private BookingStatus status;


}