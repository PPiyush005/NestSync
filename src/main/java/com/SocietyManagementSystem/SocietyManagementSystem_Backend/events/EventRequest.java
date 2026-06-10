package com.SocietyManagementSystem.SocietyManagementSystem_Backend.events;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class EventRequest {

    private String eventName;
    private String description;
    private String category;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue;
    private String organizer;
    private Long blockId; // optional, null = society-wide
}