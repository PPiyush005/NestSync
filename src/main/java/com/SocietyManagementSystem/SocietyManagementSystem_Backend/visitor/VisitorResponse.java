package com.SocietyManagementSystem.SocietyManagementSystem_Backend.visitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorResponse {

    private Long visitorId;
    private String visitorName;
    private String mobileNo;
    private String purpose;
    private LocalDate visitDate;
    private LocalTime entryTime;
    private LocalTime exitTime;
    private Long flatId;
    private String flatAddress;


}