package com.SocietyManagementSystem.SocietyManagementSystem_Backend.visitor;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class VisitorRequest {

    private String visitorName;
    private String mobileNo;
    private String purpose;
    private LocalDate visitDate;
    private LocalTime entryTime;
    private LocalTime exitTime; // optional, can be null
    private Long flatId;
}