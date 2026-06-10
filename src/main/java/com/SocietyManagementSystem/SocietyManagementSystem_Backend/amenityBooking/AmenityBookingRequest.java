package com.SocietyManagementSystem.SocietyManagementSystem_Backend.amenityBooking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AmenityBookingRequest {

    private String bookedByName;
    private String bookedByMobile;
    private String amenityType;   // "GYM" or "BANQUET_HALL"
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;       // optional
}