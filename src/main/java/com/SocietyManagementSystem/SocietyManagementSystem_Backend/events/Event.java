package com.SocietyManagementSystem.SocietyManagementSystem_Backend.events;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
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
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String eventName;

    @Column(length = 2000)
    private String description;

    private String category; // e.g. FESTIVAL, MEETING, SPORTS, CULTURAL, OTHER

    private LocalDate eventDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String venue; // free text: "Banquet Hall", "Garden", "Flat 101", etc.

    private String organizer; // name of person or committee organizing

    @ManyToOne
    @JoinColumn(name = "block_id", nullable = true)
    private Blocks block; // null = society-wide
}