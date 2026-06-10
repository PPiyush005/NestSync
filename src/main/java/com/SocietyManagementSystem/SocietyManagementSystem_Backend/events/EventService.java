package com.SocietyManagementSystem.SocietyManagementSystem_Backend.events;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.BlockRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.AuthHelper;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final BlockRepository blockRepository;
    private final AuthHelper authHelper;

    public EventService(EventRepository eventRepository,
                        BlockRepository blockRepository,
                        AuthHelper authHelper) {
        this.eventRepository = eventRepository;
        this.blockRepository = blockRepository;
        this.authHelper = authHelper;
    }

    public EventResponse addEvent(EventRequest request) {
        Event event = new Event();
        event.setEventName(request.getEventName());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setEventDate(request.getEventDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setVenue(request.getVenue());
        event.setOrganizer(request.getOrganizer());

        if (request.getBlockId() != null) {
            validateSecretaryBlockAccess(request.getBlockId());
            Blocks block = blockRepository.findById(request.getBlockId())
                    .orElseThrow(() -> new RuntimeException("Block not found with id: " + request.getBlockId()));
            event.setBlock(block);
        }

        return toResponse(eventRepository.save(event));
    }

    // Get By Id
    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        return toResponse(event);
    }

    // Get All
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Block
    public List<EventResponse> getEventsByBlock(Long blockId) {
        blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found with id: " + blockId));
        return eventRepository.findByBlock_BlockId(blockId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Society-Wide Events
    public List<EventResponse> getSocietyWideEvents() {
        return eventRepository.findByBlockIsNull()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Upcoming Events (today and future)
    public List<EventResponse> getUpcomingEvents() {
        LocalDate today = LocalDate.now();
        return eventRepository.findByEventDateAfterOrEventDateEquals(today, today)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Past Events
    public List<EventResponse> getPastEvents() {
        return eventRepository.findByEventDateBefore(LocalDate.now())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        event.setEventName(request.getEventName());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setEventDate(request.getEventDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setVenue(request.getVenue());
        event.setOrganizer(request.getOrganizer());

        if (request.getBlockId() != null) {
            validateSecretaryBlockAccess(request.getBlockId());
            Blocks block = blockRepository.findById(request.getBlockId())
                    .orElseThrow(() -> new RuntimeException("Block not found with id: " + request.getBlockId()));
            event.setBlock(block);
        } else {
            event.setBlock(null);
        }

        return toResponse(eventRepository.save(event));
    }

    private void validateSecretaryBlockAccess(Long blockId) {
        User currentUser = authHelper.getCurrentUser();
        if (currentUser.getRole().name().equals("SECRETARY")) {
            if (!currentUser.getBlockId().equals(blockId)) {
                throw new RuntimeException("Access denied: You can only manage your own block");
            }
        }
    }

    // Delete Event
    public String deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        eventRepository.delete(event);
        return "Event deleted successfully with id: " + id;
    }

    // Helper: Entity -> Response
    private EventResponse toResponse(Event event) {
        Long blockId = null;
        String blockName = null;
        if (event.getBlock() != null) {
            blockId = event.getBlock().getBlockId();
            blockName = event.getBlock().getBlockName();
        }
        return new EventResponse(
                event.getEventId(),
                event.getEventName(),
                event.getDescription(),
                event.getCategory(),
                event.getEventDate(),
                event.getStartTime(),
                event.getEndTime(),
                event.getVenue(),
                event.getOrganizer(),
                blockId,
                blockName
        );
    }
}