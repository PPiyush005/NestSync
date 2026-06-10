package com.SocietyManagementSystem.SocietyManagementSystem_Backend.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {

    private Long eventId;
    private String eventName;
    private String description;
    private String category;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue;
    private String organizer;
    private Long blockId;     // null if society-wide
    private String blockName; // null if society-wide


}