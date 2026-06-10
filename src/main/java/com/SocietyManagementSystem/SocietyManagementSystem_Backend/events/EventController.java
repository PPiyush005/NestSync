package com.SocietyManagementSystem.SocietyManagementSystem_Backend.events;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/add")
    public ResponseEntity<EventResponse> addEvent(@RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.addEvent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/block/{blockId}")
    public ResponseEntity<List<EventResponse>> getEventsByBlock(@PathVariable Long blockId) {
        return ResponseEntity.ok(eventService.getEventsByBlock(blockId));
    }

    @GetMapping("/society-wide")
    public ResponseEntity<List<EventResponse>> getSocietyWideEvents() {
        return ResponseEntity.ok(eventService.getSocietyWideEvents());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @GetMapping("/past")
    public ResponseEntity<List<EventResponse>> getPastEvents() {
        return ResponseEntity.ok(eventService.getPastEvents());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id,
                                                     @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }
}