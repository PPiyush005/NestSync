package com.SocietyManagementSystem.SocietyManagementSystem_Backend.visitor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    List<Visitor> findByFlat_FlatId(Long flatId);

    List<Visitor> findByVisitDate(LocalDate visitDate);

    List<Visitor> findByExitTimeIsNull(); // currently inside
}