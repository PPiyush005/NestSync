package com.SocietyManagementSystem.SocietyManagementSystem_Backend.visitor;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
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
@Table(name = "visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorId;

    private String visitorName;

    private String mobileNo;

    private String purpose; // e.g. DELIVERY, GUEST, MAINTENANCE, OTHER

    private LocalDate visitDate;

    private LocalTime entryTime;

    private LocalTime exitTime; // null means still inside

    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = false)
    private Flats flat;
}