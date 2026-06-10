package com.SocietyManagementSystem.SocietyManagementSystem_Backend.complaint;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintId;

    private String title;

    @Column(length = 2000)
    private String description;

    private String category; // e.g. PLUMBING, ELECTRICAL, NOISE, CLEANLINESS, OTHER

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    private LocalDate complaintDate;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "flat_id", nullable = false)
    private Flats flat;  // was: private Flat flat
}