package com.SocietyManagementSystem.SocietyManagementSystem_Backend.maintenance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenancePaymentResponse {

    private Long paymentId;
    private Double amount;
    private Integer month;
    private Integer year;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private PaymentStatus status;
    private String remarks;
    private Long flatId;
    private String flatAddress;

}