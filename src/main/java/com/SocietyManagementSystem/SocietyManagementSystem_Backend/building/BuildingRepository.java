package com.SocietyManagementSystem.SocietyManagementSystem_Backend.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository
        extends JpaRepository<Buildings, Long> {
}
