package com.SocietyManagementSystem.SocietyManagementSystem_Backend.maintenance;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "maintenance_payments")
public class MaintenancePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Double amount;

    private Integer month;  // 1-12

    private Integer year;   // e.g. 2025

    private LocalDate dueDate;

    private LocalDate paidDate; // null if not yet paid

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // PAID, UNPAID, OVERDUE

    private String remarks; // optional note e.g. "Late payment"

    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = false)
    private Flats flat;
}