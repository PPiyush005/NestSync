package com.SocietyManagementSystem.SocietyManagementSystem_Backend.events;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByBlock_BlockId(Long blockId);

    List<Event> findByBlockIsNull(); // society-wide events

    List<Event> findByEventDateAfterOrEventDateEquals(LocalDate after, LocalDate equals); // upcoming

    List<Event> findByEventDateBefore(LocalDate date); // past events
}