package com.SocietyManagementSystem.SocietyManagementSystem_Backend.maintenance;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MaintenancePaymentRequest {

    private Double amount;
    private Integer month;    // 1-12
    private Integer year;
    private LocalDate dueDate;
    private String remarks;   // optional
    private Long flatId;
}